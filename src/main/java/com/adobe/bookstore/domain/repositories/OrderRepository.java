package com.adobe.bookstore.domain.repositories;
import com.adobe.bookstore.domain.entities.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public interface OrderRepository {
    List<Order> getAllOrders();
    Order getOrderById(String id);
    void saveOrder(Order order);
    void deleteOrder(String id);
}
