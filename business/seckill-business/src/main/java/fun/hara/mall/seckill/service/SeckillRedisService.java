package fun.hara.mall.seckill.service;

import java.util.Date;

/**   
 * 秒杀相关的Redis服务
 * @Author: hanaii 
 */
public interface SeckillRedisService {
    /**
     * 保存新增的、秒杀时间在指定时间段的商品到Redis
     * [startTime,endTime)
     * @param startTime
     * @param endTime
     */
    void saveTimeAreaNewSeckillProduct(Date startTime, Date endTime);
}
