package fun.hara.mall.product.api;

import fun.hara.mall.product.domain.Product;

import java.util.List;

/**   
 * 商品服务
 * @Author: hanaii 
 */
public interface ProductService {
    List<Product> selectAll();

    void update(Product product, Long id);

    Product selectById(Long id);

}
