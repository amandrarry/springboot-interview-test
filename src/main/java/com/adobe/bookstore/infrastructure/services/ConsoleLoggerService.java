package com.adobe.bookstore.infrastructure.services;

import com.adobe.bookstore.domain.services.LoggerService;
import org.springframework.stereotype.Component;

@Component
public class ConsoleLoggerService implements LoggerService {
    public void log(String message) {
        System.out.println(message);
    }
}
