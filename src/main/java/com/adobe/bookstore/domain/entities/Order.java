package com.adobe.bookstore.domain.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private final String id;
    private List<OrderItem> orderItemList;

    public Order(String id, List<OrderItem> orderItemList) {
        this.id = id;
        this.orderItemList = orderItemList;
    }

    /**
     * Returns the Order in a Map format.
     * @return map format of an Order domain entity.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> serializedOrder = new HashMap<>();
        serializedOrder.put("id", this.id);
        List<Map<String, Object>> serializedOrderItemsList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            serializedOrderItemsList.add(orderItem.toMap());
        }
        serializedOrder.put("orderItemList", serializedOrderItemsList);
        return serializedOrder;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
