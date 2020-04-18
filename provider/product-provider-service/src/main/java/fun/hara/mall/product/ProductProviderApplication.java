package fun.hara.mall.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductProviderApplication.class, args);
        System.out.println("Product Provider Application Running");
    }
}
