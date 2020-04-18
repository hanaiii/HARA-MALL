package fun.hara.mall.product.service;


import fun.hara.mall.product.api.EchoService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String str) {
        return str;
    }
}
