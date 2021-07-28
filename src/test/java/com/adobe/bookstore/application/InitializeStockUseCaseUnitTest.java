package com.adobe.bookstore.application;
import com.adobe.bookstore.application.use_cases.InitializeStockUseCase;
import com.adobe.bookstore.domain.entities.Book;
import com.adobe.bookstore.domain.repositories.BookRepository;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InitializeStockUseCaseUnitTest {

    @InjectMocks
    InitializeStockUseCase initializeStockUseCase;

    @Mock
    GetInitialConfigurationService getInitialConfigurationService;
    @Mock
    BookRepository bookRepository;

    @Captor
    ArgumentCaptor<Book> bookCaptor;

    @Test
    public void check_if_stock_is_initialized() throws Exception {
        JSONArray mockedStock = new JSONArray("[\n" +
                "  {\n" +
                "    \"id\": \"ae1666d6-6100-4ef0-9037-b45dd0d5bb0e\",\n" +
                "    \"name\": \"adipisicing culpa Lorem laboris adipisicing\",\n" +
                "    \"quantity\": 0\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"22d580fc-d02e-4f70-9980-f9693c18f6e0\",\n" +
                "    \"name\": \"dolore aliqua sint ipsum laboris\",\n" +
                "    \"quantity\": 5\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": \"d02b58ae-8731-451c-9acb-1941adf88501\",\n" +
                "    \"name\": \"ullamco do voluptate cillum amet\",\n" +
                "    \"quantity\": 4\n" +
                "  }]");

        // Mocking responses
        when(getInitialConfigurationService.retrieveInitialStockAsJSON()).thenReturn(mockedStock);

        this.initializeStockUseCase.initialize_stock();

        // Verify calls to saveOrder in repository are as expected
        verify(bookRepository, times(3)).saveBook(bookCaptor.capture());
        List<Book> bookList = bookCaptor.getAllValues();
        Book book1 = bookList.get(0);
        assert book1.getStockQuantity() == 0;
        assert book1.getId().equals("ae1666d6-6100-4ef0-9037-b45dd0d5bb0e");
        assert book1.getTitle().equals("adipisicing culpa Lorem laboris adipisicing");
        Book book2 = bookList.get(1);
        assert book2.getStockQuantity() == 5;
        assert book2.getId().equals("22d580fc-d02e-4f70-9980-f9693c18f6e0");
        assert book2.getTitle().equals("dolore aliqua sint ipsum laboris");
        Book book3 = bookList.get(2);
        assert book3.getStockQuantity() == 4;
        assert book3.getId().equals("d02b58ae-8731-451c-9acb-1941adf88501");
        assert book3.getTitle().equals("ullamco do voluptate cillum amet");
    }
}
