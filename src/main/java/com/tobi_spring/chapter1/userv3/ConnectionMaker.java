package com.tobi_spring.chapter1.userv3;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {

    Connection makeConnection() throws ClassNotFoundException, SQLException;
}
