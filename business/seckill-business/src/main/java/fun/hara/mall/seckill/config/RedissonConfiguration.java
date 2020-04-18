package fun.hara.mall.seckill.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Bean
    public Redisson redissonBean(){
        // 单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        return (Redisson) Redisson.create(config);
    }
}