package fun.hara.mall.seckill.task;

import fun.hara.mall.common.util.DateUtil;
import fun.hara.mall.product.domain.Product;
import fun.hara.mall.seckill.domain.SeckillKeys;
import fun.hara.mall.seckill.service.SeckillRedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 定时添加秒杀商品任务
 */
@Component
public class SeckillProductPushTask {

    @Resource
    private SeckillRedisService seckillRedisService;


    /**
     *   每个时间段的大小，必须能被24整除。
     *   如设置为6，则表示有4个时间段：[0,6），[6,12)，[12,18)，[18,24)
     */
    @Value("${seckill.time-gap}")
    private Integer timeGap;



    /**
     * 每5秒将新增的该时间段秒杀商品加载到Redis中
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void loadSeckillProductToRedis(){
        if(timeGap == null || timeGap < 0 || 24%timeGap != 0){
        }
        // 获取当前时间对应的时间段
        Date[] currentTimeArea = DateUtil.getCurrentTimeArea(timeGap);

        seckillRedisService.saveTimeAreaNewSeckillProduct(currentTimeArea[0], currentTimeArea[1]);

    }
}
