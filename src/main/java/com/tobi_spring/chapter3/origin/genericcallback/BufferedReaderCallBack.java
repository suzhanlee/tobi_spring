package com.tobi_spring.chapter3.origin.genericcallback;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallBack {

    Integer doSomethingWithReader(BufferedReader br) throws IOException;
}
