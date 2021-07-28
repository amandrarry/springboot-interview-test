package com.adobe.bookstore.application;

import com.adobe.bookstore.application.responses.RetrieveOrdersResponse;
import com.adobe.bookstore.application.use_cases.RetrieveOrdersUseCase;
import com.adobe.bookstore.domain.repositories.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveOrdersUseCaseUnitTest {

    @InjectMocks
    RetrieveOrdersUseCase retrieveOrdersUseCase;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void returnsAllOrders() {
        RetrieveOrdersResponse orders = this.retrieveOrdersUseCase.retrieveAllOrders();
        verify(orderRepository, times(1)).getAllOrders();
    }
}
