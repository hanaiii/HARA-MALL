package fun.hara.mall.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**   
 * 秒杀订单信息消息
 * @Author: hanaii 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrderInfoMessage {
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品在Redis中对应的HASH key
     */
    private String key;

    /**
     * 下单时间
     */
    private Date orderTime;
}
