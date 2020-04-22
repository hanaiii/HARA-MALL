package fun.hara.mall.seckill.controller;

import fun.hara.mall.common.dto.Result;
import fun.hara.mall.common.util.Oauth2Util;
import fun.hara.mall.order.domain.Order;
import fun.hara.mall.seckill.service.SeckillService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/seckill")
@Api(value = "SeckillController", tags = {"秒杀管理接口"})
public class SeckillController {


    @Resource
    private SeckillService seckillService;

    /**
     * 下单
     *
     * @param productId 商品 id
     * @param count     商品数量
     * @return
     */
    @PostMapping("/{productId}")
    @ApiOperation("秒杀下单")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true, dataType = "Long")
    @GlobalTransactional(rollbackFor = Exception.class)
    public Result<Void> order(@PathVariable("productId") Long productId){
        return seckillService.order(Oauth2Util.getUserId(), productId);
    }

    /**
     * 获取当前用户的秒杀订单
     * @return
     */
    @ApiOperation("获取当前用户的秒杀订单状态")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true, dataType = "Long")
    @GetMapping("/seckillOrder/{productId}")
    public Result<Order> getSeckillOrder(@PathVariable("productId") Long productId){
        return seckillService.getSeckillOrder(productId, Oauth2Util.getUserId());
    }
}
