package com.adobe.bookstore.application.responses;


import com.adobe.bookstore.domain.entities.Order;

import java.util.List;

public class RetrieveOrdersResponse {
    List<Order> listOfOrders;

    public RetrieveOrdersResponse(List<Order> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }
}
