package fun.hara.mall.seckill.service.impl;

import fun.hara.mall.common.util.DateUtil;
import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.product.domain.Product;
import fun.hara.mall.seckill.domain.SeckillKeys;
import fun.hara.mall.seckill.service.SeckillRedisService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SeckillRedisServiceImpl implements SeckillRedisService {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Reference
    private ProductService productService;

    @Override
    public void saveTimeAreaNewSeckillProduct(Date startTime, Date endTime) {
        /*
            获取Redis中已存在的秒杀商品
            存储类型 HASH
                seckill_product_小时：
                                        id：商品
                                        id：商品
        */
        // 获取该时间段中已经存在的商品
        String key = DateUtil.format(startTime, "HH");
        BoundHashOperations<String, Long, Product> boundHashOps = redisTemplate
                .boundHashOps(SeckillKeys.REDIS_SECKILL_PRODUCT_PREFIX + key);
        Set<Long> keys = boundHashOps.keys();

        // 根据时间段，查询不存在Redis中的秒杀商品
        List<Product> products = productService.selectSeckillProduct(startTime, endTime, keys);

        // 将查出来的秒杀商品，存入数据库
        for (Product product : products) {
            boundHashOps.put(product.getId(), product);
        }
    }
}
