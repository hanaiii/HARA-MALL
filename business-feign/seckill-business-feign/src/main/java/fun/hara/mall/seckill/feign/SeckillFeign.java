package fun.hara.mall.seckill.feign;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("seckill-business") // 服务名
@RequestMapping("/seckill")
public interface  SeckillFeign {
    @GetMapping("/seckillOrder/{productId}")
    String getSeckillOrder(@Param("productId") Long productId);
}
