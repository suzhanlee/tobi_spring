package com.tobi_spring.chapter3.genericcallback;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallBack {

    Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
