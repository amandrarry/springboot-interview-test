package com.adobe.bookstore.infrastructure.repositories;

import com.adobe.bookstore.domain.entities.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
// TODO: Run with test environment Database?
// @SpringBootTest(classes = Application.class)
public class JPABookRepositoryUnitTest {

    @InjectMocks
    JPABookRepository jpaBookRepository;

    @Mock
    JPABookBridge jpaBookBridge;

    @Captor
    ArgumentCaptor<Book> bookCaptor;

    @Test
    public void testGetBookByID() {
        jpaBookRepository.getBookById("0");
        verify(jpaBookBridge, times(1)).findById("0");
    }

    @Test
    public void testDeleteBook() {
        jpaBookRepository.deleteBook("0");
        verify(jpaBookBridge, times(1)).deleteById("0");
    }
}
