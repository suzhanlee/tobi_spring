package com.tobi_spring.chapter3.origin.userv2;

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
