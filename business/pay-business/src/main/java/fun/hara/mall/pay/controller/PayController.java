package fun.hara.mall.pay.controller;

import fun.hara.mall.common.dto.Result;
import fun.hara.mall.pay.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**   
 * 支付接口
 * @Author: hanaii 
 */
@RestController
@RequestMapping("/pay")
@Api(value = "PayController" , tags = {"支付接口"})
public class PayController {
    @Resource
    private PayService payService;

    @ApiOperation("支付")
    @ApiImplicitParam(name = "productId", value = "订单id", required = true, dataType = "Long")
    @PostMapping("/{orderId}")
    public Result<Void> pay(@PathVariable("orderId") Long orderId){
        return payService.pay(orderId);
    }

}
