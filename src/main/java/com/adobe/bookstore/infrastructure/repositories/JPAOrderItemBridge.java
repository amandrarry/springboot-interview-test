package com.adobe.bookstore.infrastructure.repositories;

import com.adobe.bookstore.infrastructure.DAOs.OrderItemDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JPAOrderItemBridge extends JpaRepository<OrderItemDAO, String> {
    List<OrderItemDAO> findByOrderId(String orderId);
}
