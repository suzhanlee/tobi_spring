package com.tobi_spring.chapter6.proxyfactorybean;

import static org.assertj.core.api.Assertions.assertThat;

import com.tobi_spring.chapter6.dynamicproxy.UppercaseHandler;
import java.lang.reflect.Proxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;

public class DynamicProxyTest {

    static interface Hello {

        String sayHello(String name);

        String sayHi(String name);

        String sayThankYou(String name);
    }

    static class HelloTarget implements Hello {

        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }

        @Override
        public String sayHi(String name) {
            return "Hi " + name;
        }

        @Override
        public String sayThankYou(String name) {
            return "Thank you " + name;
        }
    }

    static class UpperCaseAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // reflection의 Method와 달리 인자로 타깃 오브젝트를 전달할 필요 없다.
            // MethodInvocation은 메소드 정보와 함께 타깃 오브젝트를 알고 있기 때문이다.
            String ret = (String) invocation.proceed();
            return ret.toUpperCase();
        }
    }

    @Test
    @DisplayName("이전에 InvocationHandler를 사용한 프록시 객체 생성, Handler의 인자에 타깃 오브젝트 사용 o")
    void simpleProxy() {
        com.tobi_spring.chapter6.proxy.Hello proxiedHello =
            (com.tobi_spring.chapter6.proxy.Hello) Proxy.newProxyInstance
                (
                    getClass().getClassLoader(),
                    new Class[]{com.tobi_spring.chapter6.proxy.Hello.class},
                    new UppercaseHandler(new com.tobi_spring.chapter6.proxy.HelloTarget())
                );

        assertThat(proxiedHello.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
    }


    @Test
    @DisplayName("MethodInterceptor를 사용한 프록시 객체 생성, interceptor의 인자에 타깃 오브젝트 사용 x")
    void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UpperCaseAdvice());

        // FactoryBean이므로 getObject()로 생성된 프록시를 가져온다.
        Hello proxiedProxy = (Hello) pfBean.getObject();

        assertThat(proxiedProxy.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedProxy.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(proxiedProxy.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
    }

}
