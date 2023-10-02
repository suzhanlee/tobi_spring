package com.tobi_spring.chapter6.proxyfactorybean;

public class AccountDao {

    private final ConnectionMaker connectionMaker;

    public AccountDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
