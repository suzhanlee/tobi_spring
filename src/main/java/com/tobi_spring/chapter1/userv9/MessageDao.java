package com.tobi_spring.chapter1.userv9;

public class MessageDao {

    private final ConnectionMaker connectionMaker;

    public MessageDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
