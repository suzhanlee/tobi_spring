package com.tobi_spring.chapter6.dynamicproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanFactory {

    @Bean(name = "message")
    public MessageFactoryBean messageBean() {
        MessageFactoryBean messageBean = new MessageFactoryBean();
        messageBean.setText("Factory Bean");
        return messageBean;
    }
}
