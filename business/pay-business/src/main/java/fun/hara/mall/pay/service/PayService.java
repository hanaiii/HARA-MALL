package fun.hara.mall.pay.service;

import fun.hara.mall.common.dto.Result;

public interface PayService {
    Result<Void> pay(Long orderId);
}
