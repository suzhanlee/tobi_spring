package com.tobi_spring.chapter1.userv5;

public class DaoFactory {

    public UserDao userDao() {
        ConnectionMaker connectionMaker = new SimpleConnectionMaker();
        return new UserDao(connectionMaker);
    }
}
