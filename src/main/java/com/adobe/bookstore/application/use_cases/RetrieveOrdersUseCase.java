package com.adobe.bookstore.application.use_cases;

import com.adobe.bookstore.application.responses.RetrieveOrdersResponse;
import com.adobe.bookstore.domain.entities.Order;
import com.adobe.bookstore.domain.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class RetrieveOrdersUseCase {

    OrderRepository orderRepository;

    @Autowired
    public RetrieveOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public RetrieveOrdersResponse retrieveAllOrders() {
        List<Order> listOfOrders = this.orderRepository.getAllOrders();
        return new RetrieveOrdersResponse(listOfOrders);
    }
}
