package fun.hara.mall.product.api;

import fun.hara.mall.product.domain.Product;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**   
 * 商品服务
 * @Author: hanaii 
 */
public interface ProductService {
    List<Product> selectAll();

    void update(Product product, Long id);

    Product selectById(Long id);

    /**
     * 查询在时间段范围内的商品 [startTime, endTime)
     * @param startTime
     * @param endTime
     * @param keys
     */
    List<Product> selectSeckillProduct(Date startTime, Date endTime, Set<Long> excluedIds);

    /**
     * 减少指定商品库存
     * @param productId
     * @param count
     * @return
     */
    int reduceStock(Long productId, Long count) ;
}
