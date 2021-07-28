package com.adobe.bookstore.application.use_cases;

import com.adobe.bookstore.application.requests.CreateOrderRequest;
import com.adobe.bookstore.domain.entities.Book;
import com.adobe.bookstore.domain.entities.OrderItem;
import com.adobe.bookstore.domain.repositories.BookRepository;
import com.adobe.bookstore.domain.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UpdateStockAfterOrderUseCase {

    BookRepository bookRepository;
    LoggerService loggerService;

    @Autowired
    public UpdateStockAfterOrderUseCase(BookRepository bookRepository, LoggerService loggerService) {
        this.bookRepository = bookRepository;
        this.loggerService = loggerService;
    }

    public void updateStock(CreateOrderRequest orderRequest) throws SQLException {
        for (OrderItem orderItem : orderRequest.getOrderItems()) {
            Book bookToUpdate = bookRepository.getBookById(orderItem.getBookId());
            if (bookToUpdate.getStockQuantity() - orderItem.getQuantity() < 0) {
                loggerService.log("Inconsistent stock status over book " +
                        bookToUpdate.getId() + ". Missing " +
                        (orderItem.getQuantity() - bookToUpdate.getStockQuantity()) + " books.");
            }
            bookToUpdate.setStockQuantity(Math.max(0, bookToUpdate.getStockQuantity() - orderItem.getQuantity()));
            bookRepository.saveBook(bookToUpdate);
        }
    }
}
