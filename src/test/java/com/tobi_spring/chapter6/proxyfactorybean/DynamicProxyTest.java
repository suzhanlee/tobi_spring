package com.tobi_spring.chapter6.proxyfactorybean;

import static org.assertj.core.api.Assertions.assertThat;

import com.tobi_spring.chapter6.dynamicproxy.UppercaseHandler;
import java.lang.reflect.Proxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

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
        // 인터페이스를 굳이 알려주지 않아도 ProxyFactoryBean에 있는 인터페이스 자동검출 기능을 사용해
        // 타깃 오브젝트가 구현하고 있는 인터페이스 정보를 알아낸다.
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());
        pfBean.addAdvice(new UpperCaseAdvice());

        // FactoryBean이므로 getObject()로 생성된 프록시를 가져온다.
        Hello proxiedProxy = (Hello) pfBean.getObject();

        assertThat(proxiedProxy.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedProxy.sayHi("Toby")).isEqualTo("HI TOBY");
        assertThat(proxiedProxy.sayThankYou("Toby")).isEqualTo("THANK YOU TOBY");
    }

    @Test
    void pointcutAdvisor() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        // 포인트컷과 어드바이스를 묶어서 한번에 추가
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UpperCaseAdvice()));

        Hello proxiedHello = (Hello) pfBean.getObject();

        assertThat(proxiedHello.sayHello("Toby")).isEqualTo("HELLO TOBY");
        assertThat(proxiedHello.sayHi("Toby")).isEqualTo("HI TOBY");
        // 메소드 이름이 포인트컷의 선정조건에 맞지 않아 부가기능이 적용되지 않는다.
        assertThat(proxiedHello.sayThankYou("Toby")).isEqualTo("Thank you Toby");
    }

}
