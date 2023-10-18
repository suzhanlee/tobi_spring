package com.tobi_spring.chapter3.practice.calculator_practice.strategy_pattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer fileReadContext(String filePath, Strategy strategy) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer result = 0;
            String line = null;

            while ((line = br.readLine()) != null) {
                result = strategy.doSomething(result, line);
            }

            br.close();
            return result;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
            }
        }
    }
}
