package fun.hara.mall.pay.service.impl;

import com.alibaba.fastjson.JSON;
import fun.hara.mall.common.dto.Result;
import fun.hara.mall.common.dto.StatusCode;
import fun.hara.mall.order.api.OrderService;
import fun.hara.mall.order.domain.Order;
import fun.hara.mall.pay.service.PayService;
import fun.hara.mall.seckill.domain.SeckillKeys;
import fun.hara.mall.seckill.feign.SeckillFeign;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

@Service
public class PayServiceImpl implements PayService {
    @Resource
    private RedisTemplate redisTemplate;
    @Reference
    private OrderService orderService;

    @Resource
    private Redisson redisson;

    @Override
    public Result<Void> pay(Long orderId) {
        // 获取用户id
        Long userId = 1L;
        // 查询对应的订单，判断是否处于待支付状态
        HashOperations opsForHash = redisTemplate.opsForHash();
        Long productId = Long.valueOf(opsForHash.get(SeckillKeys.REDIS_SECKILL_SIMPLE_ORDER_PRODUCT, orderId).toString());
        if(productId == null){
            // 订单不存在
            return new Result<>(true, StatusCode.ERROR, "订单不存在！");
        }
        RLock lock = redisson.getLock("Pay:" + userId + ":" + productId);
        try{
            lock.lock(10, TimeUnit.SECONDS);
            Order order = (Order) opsForHash.get(SeckillKeys.REDIS_SECKILL_ORDER_KEY+productId, userId);
            if(order.getState() == 1){
                // 等待支付才支付
                if(order == null){
                    // 订单不存在
                    return new Result<>(true, StatusCode.ERROR, "订单不存在！");
                }
                // 修改订单状态（支付成功）
                order.setState(2);
                // 写入缓存和数据库
                opsForHash.put(SeckillKeys.REDIS_SECKILL_ORDER_KEY+productId, userId, order);
                orderService.update(order, orderId);
                return new Result<>(true, StatusCode.OK, "支付成功！");
            }
        }finally{
            if(lock.isLocked()){
                lock.unlock();
            }
        }

        return new Result<>(true, StatusCode.OK, "支付失败！");

    }
}
