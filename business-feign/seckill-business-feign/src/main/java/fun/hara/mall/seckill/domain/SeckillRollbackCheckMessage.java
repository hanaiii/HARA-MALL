package fun.hara.mall.seckill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**   
 * 用户某一商品的秒杀订单信息
 * @Author: hanaii 
 */
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeckillRollbackCheckMessage {
    private Long userId;
    private Long productId;
}
