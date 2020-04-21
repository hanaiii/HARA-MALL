package fun.hara.mall.order;

import fun.hara.mall.common.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("fun.hara.mall.order.dao")
public class OrderProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderProviderApplication.class, args);
        System.out.println("Order Provider Application Running");
    }
    @Bean
    public IdWorker idWorker(){
        return  new IdWorker();
    }
}
