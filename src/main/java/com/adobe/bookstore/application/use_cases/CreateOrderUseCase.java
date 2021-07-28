package com.adobe.bookstore.application.use_cases;

import com.adobe.bookstore.application.responses.CreateOrderResponse;
import com.adobe.bookstore.domain.entities.Book;
import com.adobe.bookstore.domain.entities.Order;
import com.adobe.bookstore.domain.entities.OrderItem;
import com.adobe.bookstore.domain.repositories.BookRepository;
import com.adobe.bookstore.domain.repositories.OrderRepository;
import com.adobe.bookstore.application.requests.CreateOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@Service
@Component
public class CreateOrderUseCase {
    OrderRepository orderRepository;
    BookRepository bookRepository;

    @Autowired
    public CreateOrderUseCase(OrderRepository orderRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) throws SQLException, InvalidStockError, BookNotFoundError {
        String newOrderId = UUID.randomUUID().toString();
        for (OrderItem orderItem : request.getOrderItems()) orderItem.setOrderId(newOrderId);
        Order newOrder = new Order(newOrderId, request.getOrderItems());
        this.checkIfStockIsCorrect(newOrder);
        orderRepository.saveOrder(newOrder);
        return new CreateOrderResponse(newOrder.getId());
    }

    private void checkIfStockIsCorrect(Order order) throws SQLException, BookNotFoundError, InvalidStockError {
        for (OrderItem orderItem : order.getOrderItemList()) {
            Book bookInStock = bookRepository.getBookById(orderItem.getBookId());
            if (bookInStock == null)
                throw new BookNotFoundError("The book with ID " + orderItem.getBookId() + " does not exist.");
            else if (bookInStock.getStockQuantity() < orderItem.getQuantity())
                throw new InvalidStockError("There is not enough stock to fulfill this order. Quantity " +
                        "requested for book with ID " + bookInStock.getId() + ": " + orderItem.getQuantity() + ".\n" +
                        "Quantity in stock: " + bookInStock.getStockQuantity() + ".");
        }
    }
}
