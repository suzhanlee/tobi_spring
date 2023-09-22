package com.tobi_spring.chapter2.userv2;

public class MessageDao {

    private final ConnectionMaker connectionMaker;

    public MessageDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
