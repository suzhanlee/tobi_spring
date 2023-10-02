package com.tobi_spring.chapter6.beanpostprocessor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
    private PlatformTransactionManager transactionManager;
    public void setTransactionManager(
        PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * 타킷을 호출하는 기능을 가진 콜백 오브젝트를 프록시로 부터 받는다.
     * 덕분에 Advice는 특정 타깃에 의존하지 않고 재사용 가능하다.
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(
            new DefaultTransactionDefinition());

        try {
            Object ret = invocation.proceed();
            transactionManager.commit(status);
            return ret;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
