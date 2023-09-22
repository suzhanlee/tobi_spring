package com.tobi_spring.chapter2.learingtest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.Matchers.is;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    void test1() {
        assertThat(testObjects.contains(this)).isEqualTo(false);
        testObjects.add(this);
    }

    @Test
    void test2() {
        assertThat(testObjects.contains(this)).isEqualTo(false);
        testObjects.add(this);
    }

    @Test
    void test3() {
        assertThat(testObjects.contains(this)).isEqualTo(false);
        testObjects.add(this);
    }
}
