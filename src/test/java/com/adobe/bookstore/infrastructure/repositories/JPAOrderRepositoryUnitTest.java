package com.adobe.bookstore.infrastructure.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
// TODO: Run with test environment Database?
// @SpringBootTest(classes = Application.class)
public class JPAOrderRepositoryUnitTest {

    @InjectMocks
    JPAOrderRepository jpaOrderRepository;

    @Mock
    JPAOrderBridge jpaOrderBridge;
    @Mock
    JPAOrderItemBridge jpaOrderItemBridge;

    @Test
    public void testGetAllOrders() {
        jpaOrderRepository.getAllOrders();
        verify(jpaOrderBridge, times(1)).findAll();
    }

    @Test
    public void testGetOrderByID() {
        jpaOrderRepository.getOrderById("0");
        verify(jpaOrderBridge, times(1)).findById("0");
    }

    @Test
    public void testDeleteOrder() {
        jpaOrderRepository.deleteOrder("0");
        verify(jpaOrderBridge, times(1)).deleteById("0");
        verify(jpaOrderItemBridge, times(1)).findByOrderId("0");
    }
}
