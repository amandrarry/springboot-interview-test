package com.adobe.bookstore.infrastructure.DAOs;

import com.adobe.bookstore.domain.entities.Order;
import com.adobe.bookstore.domain.entities.OrderItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class OrderDAO {
    @Id
    @Column
    private String id;

    public OrderDAO(String id){
        this.id = id;
    }

    public OrderDAO(Order order) {
        this.id = order.getId();
    }


    public OrderDAO() {}

    public Order toOrder(List<OrderItemDAO> listOfOrderItemDAOs) {
        List<OrderItem> listOfOrderItems = new ArrayList<>();
        for (OrderItemDAO orderItemDAO : listOfOrderItemDAOs) {
            listOfOrderItems.add(orderItemDAO.toOrderItem());
        }
        return new Order(this.id, listOfOrderItems);
    }

    public String getId() {
        return id;
    }
}
