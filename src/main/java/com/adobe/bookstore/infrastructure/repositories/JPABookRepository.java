package com.adobe.bookstore.infrastructure.repositories;

import com.adobe.bookstore.domain.entities.Book;
import com.adobe.bookstore.domain.repositories.BookRepository;
import com.adobe.bookstore.infrastructure.DAOs.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public class JPABookRepository implements BookRepository {
    JPABookBridge jpaBookBridge;

    @Autowired
    public JPABookRepository(JPABookBridge jpaBookBridge) {
        this.jpaBookBridge = jpaBookBridge;
    }
    H2DBConnectionProvider connectionProvider = new H2DBConnectionProvider();

    @Override
    public Book getBookById(String id) {
        try {
            if (jpaBookBridge.findById(id).isPresent()) return jpaBookBridge.findById(id).get().toBook();
            else return null;
        } catch (Exception e) {
            throw new JpaSystemException((RuntimeException) e);
        }
    }

    @Override
    public void saveBook(Book book) {
        try {
            BookDAO bookDAO = new BookDAO(book);
            jpaBookBridge.save(bookDAO);
        } catch (Exception e) {
            throw new JpaSystemException((RuntimeException) e);
        }
    }

    @Override
    public void deleteBook(String id) {
        try {
            jpaBookBridge.deleteById(id);
        } catch (Exception e) {
            throw new JpaSystemException((RuntimeException) e);
        }
    }
}
