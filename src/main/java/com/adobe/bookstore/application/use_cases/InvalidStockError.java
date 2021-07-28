package com.adobe.bookstore.application.use_cases;


public class InvalidStockError extends Exception {
    String errorMessage;

    public InvalidStockError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
