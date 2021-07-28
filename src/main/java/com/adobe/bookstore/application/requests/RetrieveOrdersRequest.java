package com.adobe.bookstore.application.requests;

import java.io.InvalidObjectException;

public class RetrieveOrdersRequest {

    String format;

    public RetrieveOrdersRequest(String format) throws InvalidObjectException {
        if (format.equals("JSON") || format.equals("CSV")) {
            this.format = format;
        } else throw new InvalidObjectException("Format must be either CSV o JSON.");
    }

    public String getFormat() {
        return format;
    }
}
