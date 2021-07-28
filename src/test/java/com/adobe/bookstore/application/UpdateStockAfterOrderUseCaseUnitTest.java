package com.adobe.bookstore.application;

import com.adobe.bookstore.application.requests.CreateOrderRequest;
import com.adobe.bookstore.application.use_cases.UpdateStockAfterOrderUseCase;
import com.adobe.bookstore.domain.entities.Book;
import com.adobe.bookstore.domain.repositories.BookRepository;
import com.adobe.bookstore.domain.services.LoggerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InvalidObjectException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UpdateStockAfterOrderUseCaseUnitTest {

    @InjectMocks
    UpdateStockAfterOrderUseCase updateStockAfterOrderUseCase;

    @Mock
    BookRepository bookRepository;
    @Mock
    LoggerService loggerService;

    @Captor
    ArgumentCaptor<Book> bookCaptor;

    @Test
    public void testStockIsUpdated() throws InvalidObjectException, SQLException {
        // The Book we try to update in stock does exist and its quantity is retrievable
        when(bookRepository.getBookById("4")).thenReturn(new Book("4", "0", 20));
        String orderItemsAsString = "[" +
                "{\n" +
                "    \"id\": \"4\",\n" +
                "    \"name\": \"0\",\n" +
                "    \"quantity\": 2\n" +
                "}\n" +
                "]";
        CreateOrderRequest request = new CreateOrderRequest(orderItemsAsString);
        this.updateStockAfterOrderUseCase.updateStock(request);
        verify(bookRepository, times(1)).saveBook(bookCaptor.capture());
        Book bookList = bookCaptor.getValue();
        assert bookList.getStockQuantity() == 18;
    }

    @Test
    public void testStockIsUpdatedWithInconsistentState() throws InvalidObjectException, SQLException {
        // The Book we try to update in stock does exist BUT updated quantity is inconsistent
        when(bookRepository.getBookById("3")).thenReturn(new Book("3", "0", 1));
        String orderItemsAsString = "[" +
                "{\n" +
                "    \"id\": \"3\",\n" +
                "    \"name\": \"0\",\n" +
                "    \"quantity\": 5\n" +
                "}\n" +
                "]";
        CreateOrderRequest request = new CreateOrderRequest(orderItemsAsString);
        this.updateStockAfterOrderUseCase.updateStock(request);
        verify(bookRepository, times(1)).saveBook(bookCaptor.capture());
        Book bookList = bookCaptor.getValue();
        assert bookList.getStockQuantity() == 0;
        verify(loggerService, times(1)).log("Inconsistent stock status over book " +
                3 + ". Missing " + 4 + " books.");
    }
}
