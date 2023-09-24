package com.tobi_spring.chapter5.ver1;

public class DuplicateUserIdException extends RuntimeException {

    public DuplicateUserIdException(Throwable cause) {
        super(cause);
    }
}
