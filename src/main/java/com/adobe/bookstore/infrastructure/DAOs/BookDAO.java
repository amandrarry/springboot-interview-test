package com.adobe.bookstore.infrastructure.DAOs;

import com.adobe.bookstore.domain.entities.Book;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.MalformedParametersException;

@Entity
@Table(name = "BOOKS")
public class BookDAO {
    @Id
    @Column
    private String id;
    @Column
    private String title;
    @Column
    private int quantity;

    public BookDAO() {}

    public BookDAO(String id, String title, int quantity) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
    }

    /**
     * ...
     *
     * @param bookAsJSON
     *            A book in JSON format.
     */
    public BookDAO(JSONObject bookAsJSON) {
        try {
            this.id = bookAsJSON.getString("id");
            this.title = bookAsJSON.getString("name");
            this.quantity = bookAsJSON.getInt("quantity");
        } catch (Exception e) {
            throw new MalformedParametersException();
        }
    }

    public BookDAO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.quantity = book.getStockQuantity();
    }


    public Book toBook() {
        return new Book(this.id, this.title, this.quantity);
    }

    public int getQuantity() { return quantity; }
    public String getId() { return id; }
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
