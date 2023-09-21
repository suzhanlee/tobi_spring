package com.tobi_spring.chapter1.userv2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {

    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:4306/test", "root", "root"
        );
    }
}
