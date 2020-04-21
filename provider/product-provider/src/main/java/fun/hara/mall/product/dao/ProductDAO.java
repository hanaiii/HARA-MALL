package fun.hara.mall.product.dao;

import fun.hara.mall.product.domain.Product;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ProductDAO extends Mapper<Product> {
    void reduceStock(@Param("id") Long id, @Param("count") Long count);
}
