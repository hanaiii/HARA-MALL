package fun.hara.mall.order.service.fallback;

import fun.hara.mall.order.domain.Order;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class OrderServiceFallback {
    public static void insert(Order order,  Throwable ex){
        System.out.println(ex);
        log.warn(ex.getMessage());
        return;
    }
}
