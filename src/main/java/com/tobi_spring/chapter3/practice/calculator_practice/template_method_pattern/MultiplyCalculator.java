package com.tobi_spring.chapter3.practice.calculator_practice.template_method_pattern;

public class MultiplyCalculator extends Calculator{


    @Override
    protected Integer doSomething(Integer result, String line) {
        result *= Integer.valueOf(line);
        return result;
    }
}
