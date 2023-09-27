package com.tobi_spring.chapter6.dynamicproxyfactorybean;

public class MessageDao {

    private final ConnectionMaker connectionMaker;

    public MessageDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
