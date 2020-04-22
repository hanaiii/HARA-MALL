package fun.hara.mall.seckill.controller;

import fun.hara.mall.common.dto.Result;
import fun.hara.mall.common.dto.StatusCode;
import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.product.domain.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "ProductController", tags = {"商品管理接口"})
@RestController
@RequestMapping("/product")
public class ProductController {
//    @Reference
//    private ProductService productService;
//
//    @GetMapping
//    @ApiOperation("查询所有商品")
//    public Result<List<Product>> selectAll(){
//        List<Product> products = productService.selectAll();
//        return new Result<>(true, StatusCode.OK,"查询成功", products);
//    }
//
//    @PutMapping("/{id}")
//    @ApiOperation("更新商品信息")
//    public Result<Void> update(@RequestBody Product product, @PathVariable("id") Long id){
//        productService.update(product, id);
//        return new Result<>(true, StatusCode.OK,"修改成功");
//    }
}
