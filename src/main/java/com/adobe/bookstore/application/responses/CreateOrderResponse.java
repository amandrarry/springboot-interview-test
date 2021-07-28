package com.adobe.bookstore.application.responses;


public class CreateOrderResponse {
    String orderId;

    public CreateOrderResponse(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
