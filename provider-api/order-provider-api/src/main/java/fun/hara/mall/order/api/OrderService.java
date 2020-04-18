package fun.hara.mall.order.api;

import fun.hara.mall.order.domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> selectAll();

    void update(Order order, Long id);

    void insert(Order order);
}
