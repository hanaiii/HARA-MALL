package fun.hara.mall.seckill.controller;

import fun.hara.mall.product.api.EchoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {
    @Reference
    private EchoService echoService;
    @GetMapping("/echo/{msg}")
    public String echo(@PathVariable("msg") String msg){
        return echoService.echo(msg);
    }
}
