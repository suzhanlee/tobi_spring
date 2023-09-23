package com.tobi_spring.chapter4.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    String id;
    String name;
    String password;

    public User() {}

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
