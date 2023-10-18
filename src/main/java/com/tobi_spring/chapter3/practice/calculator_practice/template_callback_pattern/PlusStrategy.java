package com.tobi_spring.chapter3.practice.calculator_practice.template_callback_pattern;

public class PlusStrategy implements Strategy {

    @Override
    public Integer doSomething(Integer result, String line) {
        result += Integer.valueOf(line);
        return result;
    }
}
