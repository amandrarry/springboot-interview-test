package com.adobe.bookstore.infrastructure.DAOs;

import com.adobe.bookstore.domain.entities.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ORDER_ITEMS")
@IdClass(CompositeId.class)
public class OrderItemDAO implements Serializable {
    @Id
    @Column
    @Schema(description = "ID of the Book this order_item relates to.", required = true)
    private String bookId;
    @Id
    @Column
    @Schema(description = "ID of the Order this order_item relates to.", required = true)
    private String orderId;
    @Column
    @Schema(description = "Quantity of books purchased.", required = true)
    private int quantity;

    public OrderItemDAO(String book_id, String order_id, int quantity) {
        this.bookId = book_id;
        this.orderId = order_id;
        this.quantity = quantity;
    }

    public OrderItemDAO(OrderItem orderItem) {
        this.orderId = orderItem.getOrderId();
        this.bookId = orderItem.getBookId();
        this.quantity = orderItem.getQuantity();
    }

    public OrderItemDAO() {

    }

    public OrderItem toOrderItem() {
        return new OrderItem(this.bookId, this.orderId, this.quantity);
    }
}
