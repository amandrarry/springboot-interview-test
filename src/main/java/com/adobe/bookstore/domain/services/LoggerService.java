package com.adobe.bookstore.domain.services;

import org.springframework.stereotype.Component;

@Component
public interface LoggerService {
    void log(String message);
}
