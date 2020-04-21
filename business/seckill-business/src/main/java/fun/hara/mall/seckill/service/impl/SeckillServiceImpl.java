package fun.hara.mall.seckill.service.impl;

import fun.hara.mall.common.dto.Result;
import fun.hara.mall.common.dto.StatusCode;
import fun.hara.mall.common.util.DateUtil;
import fun.hara.mall.common.util.IdWorker;
import fun.hara.mall.order.domain.Order;
import fun.hara.mall.order.domain.factory.SeckillOrderFactory;
import fun.hara.mall.seckill.domain.SeckillKeys;
import fun.hara.mall.seckill.domain.SeckillOrderInfoMessage;
import fun.hara.mall.seckill.service.SeckillService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Resource
    private IdWorker idWorker;
    @Override
    public Result<Void> order(Long userId, Long productId) {
        // 查询是否正在进行中的秒杀订单（同一时间只允许用户对某个商品只有一单正在进行中的秒杀订单）
        Order existOrder = (Order) redisTemplate.opsForHash().get(SeckillKeys.REDIS_SECKILL_ORDER_KEY+productId, userId);
        if(existOrder != null && existOrder.getState() != 3){
            return new Result<>(false, StatusCode.ERROR, "已存在正在进行中的秒杀订单！");
        }

        // 判断用户是否重复排队（上面的秒杀订单判断是消费者已经消费了消息，这里的判断是防止消费者消费前，用户重复排队）
        // 自增，若结果为1则表示之前不在队列中，否则表示已经在队列中
        Long inQueueCount = redisTemplate.opsForHash().increment(SeckillKeys.REDIS_USER_IN_QUEUE_COUNT_KEY, userId, 1);
        if(inQueueCount > 1){
            return new Result<>(false, StatusCode.ERROR, "已经在排队了！请稍等");
        }

        // 获取当前时间段对应的key
        Date[] currentTimeArea = DateUtil.getCurrentTimeArea(6);
        String key = SeckillKeys.REDIS_SECKILL_PRODUCT_PREFIX + DateUtil.format(currentTimeArea[0], "HH");

        // 将数据发送给 MQ
        SeckillOrderInfoMessage orderInfo = new SeckillOrderInfoMessage(idWorker.nextId(), productId, userId, key, new Date());
        rocketMQTemplate.syncSend(SeckillKeys.MQ_TOPIC, orderInfo);

        // 向Redis 插入排队中的订单信息
        Order order = SeckillOrderFactory.getQueuingSeckillOrder(orderInfo);
        redisTemplate.opsForHash().put(SeckillKeys.REDIS_SECKILL_ORDER_KEY+productId, userId, order);
        return new Result<>(true, StatusCode.OK, "订单已提交，进入排队状态");
    }

    @Override
    public Result<Order> getSeckillOrder(Long productId, Long userId) {
        // 返回订单信息
        Order order = (Order)  redisTemplate.opsForHash().get(SeckillKeys.REDIS_SECKILL_ORDER_KEY+productId, userId);
        return new Result<>(true, StatusCode.OK, "查询秒杀订单成功", order);
    }
}
