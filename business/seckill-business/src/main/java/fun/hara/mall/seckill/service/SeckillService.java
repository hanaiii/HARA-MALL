package fun.hara.mall.seckill.service;

import fun.hara.mall.common.dto.Result;
import fun.hara.mall.order.domain.Order;

/**   
 * 秒杀服务
 * @Author: hanaii 
 */
public interface SeckillService {
    /**
     * 下单
     * @param userId
     * @param productId
     * @return
     */
    Result<Void> order(Long userId, Long productId);

    /**
     * 获取用户的秒杀订单
     * @param userId
     * @return
     */
    Result<Order> getSeckillOrder(Long productId, Long userId);
}
