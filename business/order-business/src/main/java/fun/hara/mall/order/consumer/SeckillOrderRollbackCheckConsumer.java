package fun.hara.mall.order.consumer;

import com.alibaba.fastjson.JSON;
import fun.hara.mall.order.api.OrderService;
import fun.hara.mall.order.domain.Order;
import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.seckill.domain.SeckillKeys;
import fun.hara.mall.seckill.domain.SeckillRollbackCheckMessage;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
/**   
 * 订单回滚检查
 * @Author: hanaii 
 */
@Service
@RocketMQMessageListener(topic = SeckillKeys.MQ_DELAY_TOPIC, consumerGroup = "seckill-delay-check-consumer")
public class SeckillOrderRollbackCheckConsumer  implements RocketMQListener<MessageExt> {
    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private Redisson redisson;
    @Reference
    private OrderService orderService;
    @Reference
    private ProductService productService;
    @Override
    @SneakyThrows
    @GlobalTransactional(rollbackFor = Exception.class)
    public void onMessage(MessageExt message) {
        // 接收消息对象
        String msgJson = new String(message.getBody(), "UTF-8");
        SeckillRollbackCheckMessage msg = JSON.parseObject(msgJson, SeckillRollbackCheckMessage.class);
        // 检查是否还未支付
        Order order = (Order) redisTemplate.opsForHash().get(SeckillKeys.REDIS_SECKILL_ORDER_KEY + msg.getProductId(), msg.getUserId());
        RLock lock = redisson.getLock("Pay:" + msg.getUserId() + ":" + msg.getProductId());
        lock.lock(10, TimeUnit.SECONDS);
        if(order.getState() == 1 ){
            // 未支付则修改为下单失败
            order.setState(3);
            // 还原数据库与Redis
            redisTemplate.opsForHash().put(SeckillKeys.REDIS_SECKILL_ORDER_KEY + msg.getProductId(), msg.getUserId(), order);
            orderService.update(order, order.getId());
            // 对应库存+1
            productService.addStock(msg.getProductId(), 1L);
        }
    }
}
