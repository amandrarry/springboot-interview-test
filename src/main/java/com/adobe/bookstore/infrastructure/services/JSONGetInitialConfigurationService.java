package com.adobe.bookstore.infrastructure.services;

import com.adobe.bookstore.application.use_cases.InitializeStockUseCase;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class JSONGetInitialConfigurationService implements com.adobe.bookstore.application.GetInitialConfigurationService {
    public JSONArray retrieveInitialStockAsJSON() {
        String fileName = "/stock.json";
        try (InputStream stockFile = InitializeStockUseCase.class.getResourceAsStream(fileName)) {
            assert stockFile != null;
            String stringifiedStockFile = IOUtils.toString(stockFile, StandardCharsets.UTF_8);
            return new JSONArray(stringifiedStockFile);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
