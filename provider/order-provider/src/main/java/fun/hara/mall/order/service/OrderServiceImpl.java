package fun.hara.mall.order.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import fun.hara.mall.common.util.IdWorker;
import fun.hara.mall.order.api.OrderService;
import fun.hara.mall.order.dao.OrderDAO;
import fun.hara.mall.order.domain.Order;
import fun.hara.mall.order.service.fallback.OrderServiceFallback;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDAO orderDAO;
    @Override
    public List<Order> selectAll() {
        return orderDAO.selectAll();
    }

    @Override
    public void update(Order order, Long id) {
        order.setId(id);
        orderDAO.updateByPrimaryKey(order);
    }

    @Override
    @SentinelResource(value="insert", fallbackClass = OrderServiceFallback.class, fallback = "insert")
    public void insert(Order order) {
        orderDAO.insert(order);
    }
}
