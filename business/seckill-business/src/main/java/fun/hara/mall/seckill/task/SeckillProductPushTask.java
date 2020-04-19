package fun.hara.mall.seckill.task;

import fun.hara.mall.common.util.DateUtil;
import fun.hara.mall.product.api.ProductService;
import fun.hara.mall.product.domain.Product;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 定时添加秒杀商品任务
 */
@Component
public class SeckillProductPushTask {
    @Resource
    private ProductService productService;

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    private static final String SECKILL_PRODUCT_PREFIX = "seckill_product_";

    @Scheduled(cron = "0/5 * * * * ?")
    public void loadSeckillProductToRedis(){
        // 将一天的时间段分割为 [0,6)、[6,12)、[12,18)、[18,24)
        // 判断当前所属时间段
        Date[] currentTimeArea = DateUtil.getCurrentTimeArea(6);
        /*
            获取Redis中已存在的秒杀商品
            存储结构 HASH
                seckill_product_小时：
                    id：商品
                    id：商品
        */
        String key = DateUtil.format(currentTimeArea[0], "HH");
        BoundHashOperations<String, Long, Product> boundHashOps = redisTemplate.boundHashOps(SECKILL_PRODUCT_PREFIX + key);
        Set<Long> keys = boundHashOps.keys();

        // 根据时间段，查询不存在Redis中的秒杀商品
        List<Product> products = productService.selectSeckillProduct(currentTimeArea[0], currentTimeArea[1], keys);
        System.out.println(products);

        // 将查出来的秒杀商品，存入数据库
        for (Product product : products) {
            boundHashOps.put(product.getId(), product);
        }
    }
}
