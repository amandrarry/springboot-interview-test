package com.adobe.bookstore.application.requests;

import com.adobe.bookstore.domain.entities.OrderItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderRequest {
    List<OrderItem> orderItems = new ArrayList<>();

    public CreateOrderRequest(String orderItemsAsString) throws InvalidObjectException {
        try {
            JSONArray orderItemsAsJSON = new JSONArray(orderItemsAsString);
            for (int i = 0; i < orderItemsAsJSON.length(); i++) {
                JSONObject orderItem = orderItemsAsJSON.getJSONObject(i);
                String bookId = orderItem.getString("id");
                String orderId = "";
                int quantity = orderItem.getInt("quantity");
                this.orderItems.add(new OrderItem(bookId, orderId, quantity));
            }
        } catch (Exception e) {
            throw new InvalidObjectException("orderItems parameter must be a correctly formatted JSON.");
        }
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
