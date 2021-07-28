package com.adobe.bookstore.application;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

@Component
public interface GetInitialConfigurationService {
    JSONArray retrieveInitialStockAsJSON();
}
