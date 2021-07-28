package com.adobe.bookstore.infrastructure.DAOs;

import java.io.Serializable;
import java.util.Objects;

public class CompositeId implements Serializable {
    private String bookId;
    private String orderId;

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

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        CompositeId pk = (CompositeId) o;
        return Objects.equals( bookId, pk.orderId ) &&
                Objects.equals( orderId, pk.bookId );
    }

    @Override
    public int hashCode() {
        return Objects.hash( bookId, orderId );
    }
}
