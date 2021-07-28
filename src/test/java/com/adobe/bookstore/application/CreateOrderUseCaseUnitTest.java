package com.adobe.bookstore.application;

import com.adobe.bookstore.application.requests.CreateOrderRequest;
import com.adobe.bookstore.application.use_cases.BookNotFoundError;
import com.adobe.bookstore.application.use_cases.CreateOrderUseCase;
import com.adobe.bookstore.application.use_cases.InvalidStockError;
import com.adobe.bookstore.domain.entities.Book;
import com.adobe.bookstore.domain.entities.Order;
import com.adobe.bookstore.domain.repositories.BookRepository;
import com.adobe.bookstore.domain.repositories.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InvalidObjectException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateOrderUseCaseUnitTest {

    @InjectMocks
    CreateOrderUseCase createOrderUseCase;

    @Mock
    OrderRepository orderRepository;
    @Mock
    BookRepository bookRepository;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    @Test
    public void testCheckIfStockIsCorrect() throws SQLException, InvalidObjectException, InvalidStockError, BookNotFoundError {
        // The Book we try to retrieve from stock does exist and its quantity is retrievable
        when(bookRepository.getBookById("4")).thenReturn(new Book("4", "0", 20));
        // Creation of a Mock request with the Order that is going to be created with the Order that is being created
        String orderItemsAsString = "[" +
                "{\n" +
                "    \"id\": \"4\",\n" +
                "    \"name\": \"0\",\n" +
                "    \"quantity\": 2\n" +
                "}\n" +
                "]";
        CreateOrderRequest request = new CreateOrderRequest(orderItemsAsString);
        this.createOrderUseCase.createOrder(request);
        verify(orderRepository, times(1)).saveOrder(orderCaptor.capture());
        Order savedOrder = orderCaptor.getValue();
        assert savedOrder.getId().length() == 36;
    }

    @Test
    public void testCheckIfStockThrowsBookNotFoundError() throws InvalidObjectException {
        // Creation of a Mock request with the Order that is going to be created with the Order that is being created
        String orderItemsAsString = "[" +
                "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"0\",\n" +
                "    \"quantity\": 18\n" +
                "}\n" +
                "]";
        CreateOrderRequest request = new CreateOrderRequest(orderItemsAsString);
        // Asserts that an BookNotFoundError Exception is thrown
        Exception exception = assertThrows(BookNotFoundError.class, () -> createOrderUseCase.createOrder(request));
    }

    @Test
    public void testCheckIfStockThrowsInvalidStockError() throws SQLException, InvalidObjectException {
        // The Book we try to retrieve from stock does exist, but there are not enough to proceed with the order
        // qty = 0
        when(bookRepository.getBookById("1")).thenReturn(new Book("1", "0", 0));
        // Creation of a Mock request with the Order that is going to be created
        // There is only one OrderItem related to it that asks qty = 18
        String orderItemsAsString2 = "[" +
                "{\n" +
                "    \"id\": \"1\",\n" +
                "    \"name\": \"0\",\n" +
                "    \"quantity\": 18\n" +
                "}\n" +
                "]";
        CreateOrderRequest request = new CreateOrderRequest(orderItemsAsString2);
        // Asserts that an BookNotFoundError Exception is thrown
        Exception exception = assertThrows(InvalidStockError.class, () -> createOrderUseCase.createOrder(request));
    }
}
