package com.tobi_spring.chapter3.genericcallback;

public interface LineCallback<T> {

    T doSomethingWithLine(String line, T value);
}
