package fun.hara.mall.order.service;

import fun.hara.mall.order.api.OrderService;
import fun.hara.mall.order.dao.OrderDAO;
import fun.hara.mall.order.domain.Order;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(version = "1.0.0")
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
    public void insert(Order order) {
        orderDAO.insert(order);
    }
}
