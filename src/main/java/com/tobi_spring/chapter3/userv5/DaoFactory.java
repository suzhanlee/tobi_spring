package com.tobi_spring.chapter3.userv5;

import com.mysql.cj.jdbc.Driver;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() throws InstantiationException, IllegalAccessException {
        UserDao dao = new UserDao();
        dao.setJdbcContext(new JdbcContext(dataSource()));
        return dao;
    }

//    @Bean
//    public JdbcContext jdbcContext() throws InstantiationException, IllegalAccessException {
//        return new JdbcContext(dataSource());
//    }

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
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
