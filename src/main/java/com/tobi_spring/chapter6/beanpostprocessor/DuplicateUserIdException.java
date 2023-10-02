package com.tobi_spring.chapter6.beanpostprocessor;

public class DuplicateUserIdException extends RuntimeException {

    public DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
