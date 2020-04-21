package fun.hara.mall.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PayBusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayBusinessApplication.class, args);
        System.out.println("Pay Business Application Running");
    }
}
