package com.tobi_spring.chapter3.practice.calculator_practice.template_callback_pattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer fileReadTemplate(String filePath, Callback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer result = 0;
            String line = null;

            while ((line = br.readLine()) != null) {
                result = callback.doSomething(result, line);
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

    public Integer calcSum(String filePath) throws IOException {
        Callback callback = new Callback() {

            @Override
            public Integer doSomething(Integer result, String line) {
                result += Integer.valueOf(line);
                return result;
            }
        };

        return fileReadTemplate(filePath, callback);
    }

    public Integer calcMultiply(String filePath) throws IOException {
        Callback callback = new Callback() {

            @Override
            public Integer doSomething(Integer result, String line) {
                result *= Integer.valueOf(line);
                return result;
            }
        };

        return fileReadTemplate(filePath, callback);
    }
}
