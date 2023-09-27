package com.tobi_spring.chapter6.dynamicproxyfactorybean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    String id;
    String name;
    String password;
    String email;

    Level level;
    int login;
    int recommend;

    public User() {
    }

    public User(String id, String name, String password, Level level, int login, int recommend, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
        this.email = email;
    }

    public void upgradeLevel() {
        Level nextLevel = level.nextLevel();
        if (nextLevel == null) {
            throw new IllegalStateException(level + "은 업그레이드가 불가능합니다");
        } else {
            level = nextLevel;
        }
    }
}
