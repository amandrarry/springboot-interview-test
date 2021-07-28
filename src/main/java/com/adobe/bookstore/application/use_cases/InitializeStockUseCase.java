package com.adobe.bookstore.application.use_cases;
import com.adobe.bookstore.application.GetInitialConfigurationService;
import com.adobe.bookstore.domain.entities.Book;
import com.adobe.bookstore.domain.repositories.BookRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class InitializeStockUseCase {
    private final GetInitialConfigurationService getInitialConfigurationService;
    private final BookRepository bookRepository;

    @Autowired
    public InitializeStockUseCase(GetInitialConfigurationService getInitialConfigurationService,
                                  BookRepository bookRepository) {
        this.getInitialConfigurationService = getInitialConfigurationService;
        this.bookRepository = bookRepository;
    }
    /**
     * Reads the initial state of the stock in order to persist it in the system database.
     */
    public void initialize_stock() {
        try {
            JSONArray stockJSONArray = getInitialConfigurationService.retrieveInitialStockAsJSON();
            for (int i = 0; i < stockJSONArray.length(); i++) {
                JSONObject bookAsJSONObject = stockJSONArray.getJSONObject(i);
                Book book = new Book(bookAsJSONObject);
                bookRepository.saveBook(book);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
