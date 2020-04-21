package fun.hara.mall.order.domain.factory;

import fun.hara.mall.order.domain.Order;
import fun.hara.mall.seckill.domain.SeckillOrderInfoMessage;

import java.math.BigDecimal;

public class SeckillOrderFactory {
    /**
     * 获取排队状态的秒杀订单
     * @param orderInfo
     * @return
     */
    public static Order getQueuingSeckillOrder(SeckillOrderInfoMessage orderInfo){
        Order order = new Order();
        order.setId(orderInfo.getOrderId());
        order.setState(0);
        order.setOrderTime(orderInfo.getOrderTime());
        order.setUserId(orderInfo.getUserId());
        order.setProductId(orderInfo.getProductId());
        return order;
    }
    /**
     * 获取待支付状态的秒杀订单
     * @param orderInfo
     * @param amount
     * @return
     */
    public static Order getWaittingPayOrder(SeckillOrderInfoMessage orderInfo, BigDecimal amount) {
        Order order = getQueuingSeckillOrder(orderInfo);
        order.setState(1);
        order.setAmount(amount);
        return order;
    }
    /**
     * 获取下单失败的秒杀订单
     * @param orderInfo
     * @param amount
     * @return
     */
    public static Order getSeckillFailedOrder(SeckillOrderInfoMessage orderInfo) {
        Order order = getQueuingSeckillOrder(orderInfo);
        order.setState(3);
        return order;
    }
}
