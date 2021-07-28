package com.adobe.bookstore.domain.entities;

import org.json.JSONObject;

import java.lang.reflect.MalformedParametersException;
import java.util.HashMap;
import java.util.Map;

public class Book {
    private String id;
    private String title;
    private int stockQuantity;

    public Book() {}

    public Book(String id, String title, int quantity) {
        this.id = id;
        this.title = title;
        this.stockQuantity = quantity;
    }

    /**
     * Returns the Book in a JSON format.
     * @param bookAsJSON: Book in a JSON format.
     */
    public Book(JSONObject bookAsJSON) {
        try {
            this.id = bookAsJSON.getString("id");
            this.title = bookAsJSON.getString("name");
            this.stockQuantity = bookAsJSON.getInt("quantity");
        } catch (Exception e) {
            throw new MalformedParametersException();
        }
    }

    /**
     * Returns the Book in a Map format.
     * @return map format of a Book domain entity.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> serializedBook = new HashMap<>();
        serializedBook.put("id", this.id);
        serializedBook.put("title", this.title);
        serializedBook.put("stockQuantity", this.stockQuantity);
        return serializedBook;
    }

    public int getStockQuantity() { return stockQuantity; }
    public String getId() { return id; }
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
