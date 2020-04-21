package fun.hara.mall.seckill.domain;

/**
 * 秒杀商品相关的key
 */
public class SeckillKeys {
    public static final String REDIS_SECKILL_PRODUCT_PREFIX = "seckill_product:";
    public static final String REDIS_USER_IN_QUEUE_COUNT_KEY = "user_in_queue_count";
    public static final String MQ_TOPIC = "seckill-order-info-topic";
    public static final String REDIS_SECKILL_ORDER_KEY = "product-seckill-order:";
}
