package com.tobi_spring.chapter3.callback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer lineReadTemplate(String filePath, LineCallback callback, int initVal)
        throws FileNotFoundException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;

        } catch (IOException e) {
            throw new RuntimeException(e);
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

    // template
    public Integer fileReadTemplate(String filePath, BufferedReaderCallBack callBack)
        throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            int ret = callBack.doSomethingWithReader(br);
            return ret;
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

    // client -> client에서 callback을 만들어 template에게 DI한 후 template을 호출한다.
    public Integer calcSum(String filePath) throws IOException {

        LineCallback sumCallback = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        };

        return lineReadTemplate(filePath, sumCallback, 0);
//        BufferedReaderCallBack sumCallback = new BufferedReaderCallBack() {
//            @Override
//            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
//                Integer sum = 0;
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    sum += Integer.valueOf(line);
//                }
//                return sum;
//            }
//        };
//
//        return fileReadTemplate(filePath, sumCallback);
    }

    public Integer calcMultiply(String filePath) throws IOException {

        LineCallback multiplyCallback = new LineCallback() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        };

        return lineReadTemplate(filePath, multiplyCallback, 1);

//        BufferedReaderCallBack multiplyCallback = new BufferedReaderCallBack() {
//            @Override
//            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
//                Integer multiply = 1;
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    multiply *= Integer.valueOf(line);
//                }
//                return multiply;
//            }
//        };
//
//        return fileReadTemplate(filePath, multiplyCallback);
    }
}
