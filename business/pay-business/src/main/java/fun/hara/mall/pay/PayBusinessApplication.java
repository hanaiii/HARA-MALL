package fun.hara.mall.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**   
 * 支付服务
 * @Author: hanaii 
 */
@SpringBootApplication
public class PayBusinessApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayBusinessApplication.class, args);
        System.out.println("Pay Business Application Running");
    }
}
