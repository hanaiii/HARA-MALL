package fun.hara.mall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderBusinessApplication.class, args);
        System.out.println("Order Business Application Running");
    }
}
