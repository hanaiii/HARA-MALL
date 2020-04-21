package fun.hara.mall.pay.controller;

import fun.hara.mall.common.dto.Result;
import fun.hara.mall.common.dto.StatusCode;
import fun.hara.mall.pay.service.PayService;
import fun.hara.mall.seckill.feign.SeckillFeign;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/pay")
@Api(value = "PayController" , tags = {"支付接口"})
public class PayController {
    @Resource
    private PayService payService;


    @PostMapping("/{orderId}")
    public Result<Void> pay(@PathVariable("orderId") Long orderId){


        return payService.pay(orderId);

    }

}
