package com.tobi_spring.chapter6.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HelloUppercaseTest {

    @Test
    void simpleDecorateProxy() {
        Hello proxiedHello = new HelloUppercase(new HelloTarget());

        assertThat(proxiedHello.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedHello.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(proxiedHello.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
    }
}