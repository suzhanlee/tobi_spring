package com.tobi_spring.chapter3.origin.genericcallback;

public interface LineCallback<T> {

    T doSomethingWithLine(String line, T value);
}
