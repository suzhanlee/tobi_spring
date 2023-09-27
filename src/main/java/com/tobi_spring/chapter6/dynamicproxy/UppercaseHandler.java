package com.tobi_spring.chapter6.dynamicproxy;

import com.tobi_spring.chapter6.proxy.Hello;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

    /**
     * 아래 래퍼런스는 무슨 기능이 필요하길래 있는걸까? 주석처리해서 테스트 해보자!
     *  -> method.invoke(target, args);
     */
    Object target;

    public UppercaseHandler(Hello target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object ref = method.invoke(target, args);

        if (ref instanceof String && method.getName().startsWith("say")) {
            return ((String) ref).toUpperCase();
        }
        return ref;
    }
}
