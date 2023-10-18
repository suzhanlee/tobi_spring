package com.tobi_spring.chapter3.origin.callback;

import static org.assertj.core.api.Assertions.assertThat;

import com.tobi_spring.chapter3.origin.callback.Calculator;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalcSumTest {

    Calculator calculator;
    String numFilePath;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        numFilePath = getClass().getResource("number.txt").getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(numFilePath)).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(numFilePath)).isEqualTo(24);
    }

}
