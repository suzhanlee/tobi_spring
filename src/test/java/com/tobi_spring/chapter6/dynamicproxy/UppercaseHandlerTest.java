package com.tobi_spring.chapter6.dynamicproxy;

import static org.assertj.core.api.Assertions.assertThat;

import com.tobi_spring.chapter6.dynamicproxy.UppercaseHandler;
import com.tobi_spring.chapter6.proxy.Hello;
import com.tobi_spring.chapter6.proxy.HelloTarget;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;

class UppercaseHandlerTest {

    @Test
    void dynamicPoxy() {
        // Proxy.newProxyInstance()로 다이내믹 프록시 생성
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
            // 동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더
            getClass().getClassLoader(),
            // 구현할 인터페이스 -> 이 인터페이스의 모든 메소드를 구현한 오브젝트가 다이내믹 프록시 오브젝트이다.
            new Class[]{Hello.class},
            // 부가기능과 위임 코드를 담은 InvocationHandler
            new UppercaseHandler(new HelloTarget())
        );

        assertThat(proxiedHello.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
    }

}