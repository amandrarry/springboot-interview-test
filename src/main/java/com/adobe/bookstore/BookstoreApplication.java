package com.adobe.bookstore;

import com.adobe.bookstore.application.use_cases.InitializeStockUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BookstoreApplication {

    private static InitializeStockUseCase initializeStockUseCase;

    @Autowired
    public BookstoreApplication(InitializeStockUseCase initializeStockUseCase) {
        BookstoreApplication.initializeStockUseCase = initializeStockUseCase;
    }

    public static void main(String[] arguments) {
        ApplicationContext context = SpringApplication.run(BookstoreApplication.class, arguments);
        // added this - get the object via the context as a bean
        InitializeStockUseCase iniStockUseCase = (InitializeStockUseCase) context.getBean("initializeStockUseCase");
        initializeStockUseCase.initialize_stock();
    }
}
