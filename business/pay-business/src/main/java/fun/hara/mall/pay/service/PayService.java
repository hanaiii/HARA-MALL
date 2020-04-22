package fun.hara.mall.pay.service;
import fun.hara.mall.common.dto.Result;

/**
 * 支付服务
 */
public interface PayService {
    Result<Void> pay(Long orderId);
}
