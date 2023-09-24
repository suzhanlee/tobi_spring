package com.tobi_spring.chapter5.ver1;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {

    Connection makeConnection() throws ClassNotFoundException, SQLException;
}
