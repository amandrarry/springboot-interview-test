package com.adobe.bookstore.application.use_cases;

public class BookNotFoundError extends Exception{
    String errorMessage;

    public BookNotFoundError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
