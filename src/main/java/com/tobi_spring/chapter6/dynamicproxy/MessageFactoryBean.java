package com.tobi_spring.chapter6.dynamicproxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

public class MessageFactoryBean implements FactoryBean<Message> {

    String text;

    // 오브젝트를 생성할 떄, 필요한 정보를 팩토리 빈의 프로퍼티로 설정해서 대신 DI 받을 수 있도록 한다.
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Message getObject() throws Exception {
        // 실제로 빈으로 사용할 오브젝트를 직접 생성한다.
        return Message.newMessage(text);
    }

    @Override
    public Class<?> getObjectType() {
        return Message.class;
    }

    @Override
    public boolean isSingleton() {
        // 픽토리 빈의 동작방식에 관한 설명이므로, 만들어진 빈 오브젝트는 싱글톤으로 스프링이 관리해줄 수 있다.
        return false;
    }
}
