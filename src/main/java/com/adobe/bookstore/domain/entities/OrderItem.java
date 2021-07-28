package com.adobe.bookstore.domain.entities;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderItem {
    private String bookId;
    private String orderId;
    private int quantity;

    public OrderItem(String book_id, String order_id, int quantity) {
        this.bookId = book_id;
        this.orderId = order_id;
        this.quantity = quantity;
    }

    public OrderItem(JSONObject orderItem) {
        this.bookId = orderItem.getString("bookId");
        this.orderId = orderItem.getString("orderId");
        this.quantity = orderItem.getInt("quantity");
    }

    /**
     * Returns the OrderItem in a Map format.
     * @return map format of an OrderItem domain entity.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> serializedOrderItem = new HashMap<>();
        serializedOrderItem.put("book_id", this.bookId);
        serializedOrderItem.put("order_id", this.orderId);
        serializedOrderItem.put("quantity", this.quantity);
        return serializedOrderItem;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
