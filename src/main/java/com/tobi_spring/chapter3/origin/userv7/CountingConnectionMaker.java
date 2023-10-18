package com.tobi_spring.chapter3.origin.userv7;

import java.sql.Connection;
import java.sql.SQLException;
import lombok.Getter;

public class CountingConnectionMaker implements ConnectionMaker {

    @Getter
    int counter = 0;
    private ConnectionMaker realConnectionMaker;
    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realConnectionMaker.makeConnection();
    }
}
