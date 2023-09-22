package com.tobi_spring.chapter2.userv2;

import com.mysql.jdbc.Driver;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() throws InstantiationException, IllegalAccessException {
        return new UserDao(dataSource());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public DataSource dataSource() throws InstantiationException, IllegalAccessException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriver(Driver.class.newInstance());
        dataSource.setUrl("jdbc:mysql://localhost:4306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    @Bean
    public AccountDao accountDao() {
        return new AccountDao(connectionMaker());
    }

    @Bean
    public MessageDao messageDao() {
        return new MessageDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
