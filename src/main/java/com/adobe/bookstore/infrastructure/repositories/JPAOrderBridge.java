package com.adobe.bookstore.infrastructure.repositories;

import com.adobe.bookstore.infrastructure.DAOs.OrderDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JPAOrderBridge extends JpaRepository<OrderDAO, String> {}
