package com.adobe.bookstore.domain.repositories;

import com.adobe.bookstore.domain.entities.Book;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Component
@Repository
public interface BookRepository {
    Book getBookById(String id) throws SQLException;
    void saveBook(Book book) throws SQLException;
    void deleteBook(String id);
}
