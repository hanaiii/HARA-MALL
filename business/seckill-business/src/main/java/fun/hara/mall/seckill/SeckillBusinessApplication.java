package fun.hara.mall.seckill;

import fun.hara.mall.common.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SeckillBusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeckillBusinessApplication.class, args);
        System.out.println("Seckill Business Application Running");
    }
    @Bean
    public IdWorker idWorker(){
        return  new IdWorker();
    }
}
