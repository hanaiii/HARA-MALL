package fun.hara.mall.product.service;

import fun.hara.mall.common.util.DateUtil;
import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.product.dao.ProductDAO;
import fun.hara.mall.product.domain.Product;
import org.apache.dubbo.config.annotation.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Override
    public  List<Product> selectSeckillProduct(Date startTime, Date endTime, Set<Long> excluedIds) {
        String start = DateUtil.formatForSQL(startTime);
        String end = DateUtil.formatForSQL(endTime);
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThan("stock", 0);
        criteria.andEqualTo("state", Product.SECKILL);
        criteria.andGreaterThanOrEqualTo("startTime", start);
        criteria.andLessThan("endTime", end);
        if(excluedIds != null && excluedIds.size()>0){
            criteria.andNotIn("id", excluedIds);
        }
        return productDAO.selectByExample(example);
    }

    @Override
    public int reduceStock(Long productId, Long count) {
        // 这里可以用一句sql实现，这里为了测试分布式锁，所以写成非原子操作
        Product product = new Product();
        product.setId(productId);
        product = productDAO.selectByPrimaryKey(product);
        product.setStock(product.getStock() - count);

        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("stock", count);
        criteria.andEqualTo("id", productId);

        return productDAO.updateByExampleSelective(product, example);
    }

}
