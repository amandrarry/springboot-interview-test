package com.adobe.bookstore.infrastructure.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DBConnectionProvider {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:file:/data/demo";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "password";

    Connection returnConnectionToH2Database() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e) {
            throw new SQLException();
        }
    }
}
