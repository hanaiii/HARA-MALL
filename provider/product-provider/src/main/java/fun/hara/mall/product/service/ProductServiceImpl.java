package fun.hara.mall.product.service;

import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.product.dao.ProductDAO;
import fun.hara.mall.product.domain.Product;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl  implements ProductService {
    @Resource
    private ProductDAO productDAO;
    @Override
    public List<Product> selectAll() {
        return productDAO.selectAll();
    }

    @Override
    public void update(Product product, Long id) {
        product.setId(id);
        productDAO.updateByPrimaryKey(product);
    }

    @Override
    public Product selectById(Long id) {
        return productDAO.selectByPrimaryKey(id);
    }
}
