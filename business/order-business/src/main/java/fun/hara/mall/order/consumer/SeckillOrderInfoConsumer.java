package fun.hara.mall.order.consumer;

import fun.hara.mall.order.api.OrderService;
import fun.hara.mall.order.domain.Order;
import fun.hara.mall.order.domain.factory.SeckillOrderFactory;
import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.product.domain.Product;
import fun.hara.mall.seckill.domain.SeckillKeys;
import fun.hara.mall.seckill.domain.SeckillOrderInfoMessage;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀订单信息消费
 */
@Service
@Log4j2
@RocketMQMessageListener(topic = SeckillKeys.MQ_TOPIC, consumerGroup = "seckill-consumer")
public class SeckillOrderInfoConsumer implements RocketMQListener<SeckillOrderInfoMessage> {
    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Reference
    private OrderService orderService;
    @Reference
    private ProductService productService;

    @Resource
    private Redisson redisson;
    @Override
    public void onMessage(SeckillOrderInfoMessage orderInfo) {
        Long productId = orderInfo.getProductId();
        // 不同用户同时只能有一个能操作库存
        RLock lock = redisson.getLock("product_stock:" + productId);
        try {
            lock.lock(10L, TimeUnit.SECONDS);
            // 查询对应秒杀时间段、对应商品是否仍然有库存
            BoundHashOperations<String, Long, Product> boundHashOps =
                    redisTemplate.boundHashOps(orderInfo.getKey());
            Product product = boundHashOps.get(productId);
            // 没有库存。更新订单信息为下单失败，商品已售完
            if(product == null || product.getStock() == 0){
                updateRedisSeckillDataToFailed(orderInfo);
                return;
            }
            // 有库存，但是商品秒杀时间不在下单时间内
            if(!(product.getStartTime().getTime() <= orderInfo.getOrderTime().getTime()
                        && product.getEndTime().getTime() > orderInfo.getOrderTime().getTime()
             )){
                updateRedisSeckillDataToFailed(orderInfo);
                return;
            }
            // 将Redis中相关的秒杀数据更新到待支付状态
            updateRedisSeckillDataToWaittingPayAndUpdateDB(product, orderInfo);

        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }


    /**
     * 将Redis中相关的秒杀数据更新到下单失败状态
     * @param orderInfo
     */
    private void updateRedisSeckillDataToFailed(SeckillOrderInfoMessage orderInfo) {
        Long userId = orderInfo.getUserId();
        Order order = SeckillOrderFactory.getSeckillFailedOrder(orderInfo);
        redisTemplate.opsForHash().put(SeckillKeys.REDIS_SECKILL_ORDER_KEY+orderInfo.getProductId(), userId, order);
        // 删除用户的排队信息
        redisTemplate.opsForHash().delete(SeckillKeys.REDIS_USER_IN_QUEUE_COUNT_KEY, userId);
    }

    /**
     * 将Redis中相关的秒杀数据更新到待支付状态
     * @param product
     * @param orderInfo
     */
    private void updateRedisSeckillDataToWaittingPayAndUpdateDB(Product product, SeckillOrderInfoMessage orderInfo) {
        BoundHashOperations<String, Long, Product> boundHashOps =
                redisTemplate.boundHashOps(orderInfo.getKey());
        // 商品库存减1
        product.setStock(product.getStock() - 1);
        Long productId = product.getId();
        boundHashOps.put(productId, product);
        // 更新订单信息到待支付状态
        Order order = SeckillOrderFactory.getWaittingPayOrder(orderInfo, product.getPrice());
        Long userId = orderInfo.getUserId();
        redisTemplate.opsForHash().put(SeckillKeys.REDIS_SECKILL_ORDER_KEY+productId, userId, order);
        // 删除用户的排队信息
        redisTemplate.opsForHash().delete(SeckillKeys.REDIS_USER_IN_QUEUE_COUNT_KEY, userId);
        // 可以定时刷入，这里每次刷入保证强一致性
        insertOrderToDB(order);
        // 放入简易的待支付订单信息
        redisTemplate.opsForHash().put(SeckillKeys.REDIS_SECKILL_SIMPLE_ORDER_PRODUCT, order.getId(), productId);
    }

    /**
     * 插入订单到数据库
     * @param order
     */
    @GlobalTransactional(rollbackFor = Exception.class)
    private void insertOrderToDB(Order order) {
        // 库存-1
        int row = productService.reduceStock(order.getProductId(),1L);
        if(row == 0){
            log.error("商品id：{}，库存更新失败", order.getProductId());
            throw new RuntimeException("库存更新失败");
        }
        // 生成订单对象
        orderService.insert(order);
    }
}