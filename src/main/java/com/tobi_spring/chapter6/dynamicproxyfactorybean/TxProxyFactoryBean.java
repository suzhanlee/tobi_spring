package com.tobi_spring.chapter6.dynamicproxyfactorybean;

import java.lang.reflect.Proxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

// 제네릭 타입을 Object로 사용한 이유는 원하는 빈의 타입을 지정할 수 있지만, 범용적으로 사용하기 위해 Object 사용
public class TxProxyFactoryBean implements FactoryBean<Object> {

    /**
     * 아래 3가지 프러퍼티는 TransactionHandler를 생성할 때 필요
     */
    Object target;
    PlatformTransactionManager transactionManager;
    String pattern;
    // UserService 이외의 인터페이스를 가진 타깃에도 적용할 수 있도록 만든다.
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTransactionManager(
        PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    /**
     * FactoryBean 인터페이스 구현 메소드 DI 받은 정보를 이용해서 TransactionHandler 를 사용하는 다이내믹 프록시를 생성한다.
     * target(UserServiceImpl) 오브젝트의 인터페이스에 대한 구현체 생성하고 그 구현체를 빈으로 등록 즉, UserService로 만든 다이내믹 프록시
     * 오브젝트를 빈으로 등록한다.
     */
    @Override
    public Object getObject() throws Exception {
        TransactionHandler txHandler = new TransactionHandler();
        txHandler.setTarget(target);
        txHandler.setTransactionManager(transactionManager);
        txHandler.setPattern(pattern);
        return Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{serviceInterface},
            txHandler
        );
    }

    @Override
    public Class<?> getObjectType() {
        // 팩토리 빈이 생성하는 오브젝트의 타입은 DI 받은 인터페이스 타입에 따라 달라진다.
        // 따라서 다양한 타입의 프록시 오브젝트 생성에 재사용할 수 있다.
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        // 싱글톤 빈이 아니라는 뜻이 아니라 getObject() 가 매번 같은 오브젝트를 리턴하지 않는다는 의미다.
        return false;
    }
}
