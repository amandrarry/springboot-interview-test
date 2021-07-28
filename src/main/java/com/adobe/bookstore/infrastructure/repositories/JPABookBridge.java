package com.adobe.bookstore.infrastructure.repositories;

import com.adobe.bookstore.infrastructure.DAOs.BookDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JPABookBridge extends JpaRepository<BookDAO, String> {
}
