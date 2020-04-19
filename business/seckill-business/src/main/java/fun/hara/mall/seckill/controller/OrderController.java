package fun.hara.mall.seckill.controller;

import fun.hara.mall.common.dto.Result;
import fun.hara.mall.common.dto.StatusCode;
import fun.hara.mall.order.api.OrderService;
import fun.hara.mall.order.domain.Order;
import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.product.domain.Product;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
@Api(value = "OrderController", tags = {"订单管理接口"})
public class OrderController {
    @Reference
    private OrderService orderService;
    @Reference
    private ProductService productService;

    @Resource
    private Redisson redisson;
    /**
     * 下单
     *
     * @param productId 商品 id
     * @param count     商品数量
     * @return
     */
    @PostMapping("/{productId}/{count}")
    @ApiOperation("下单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "count", value = "下单数量", required = true, dataType = "Long")
    })
    @GlobalTransactional(rollbackFor = Exception.class)
    public Result<Void> order(
            @PathVariable("productId") Long productId,
            @PathVariable("count") Long count) {
        // 查询对应商品库存
        RLock lock = redisson.getLock("product_stock:" + productId);
        Product product;
        try {
            lock.lock(10L, TimeUnit.SECONDS);
            product = productService.selectById(productId);
            // 库存足够才允许下单
            if(product == null || product.getStock() < count){
                return new Result<>(false, StatusCode.ERROR, "下单失败");
            }
            // 修改库存（超卖问题 -> 分布式锁）
            product.setStock(product.getStock() - count);
            // 保存
            productService.update(product, productId);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
        // 生成订单对象
        Order order = generateOrder(product, count);
        orderService.insert(order);
        return new Result<>(true, StatusCode.OK, "下单成功");
    }

    /**
     * 生成订单对象
     * @param product
     * @param count
     * @return
     */
    private Order generateOrder(Product product, Long count) {
        BigDecimal amount = product.getPrice().multiply(new BigDecimal(count));
        Order order = new Order();
        order.setAmount(amount);
        order.setOrderTime(new Date());
        return order;
    }
}
