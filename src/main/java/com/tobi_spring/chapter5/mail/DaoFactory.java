package com.tobi_spring.chapter5.mail;

import com.mysql.cj.jdbc.Driver;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DaoFactory {

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
//        mailSender.setHost("mail.server.com");
//        return mailSender;
    }

    @Bean
    public PlatformTransactionManager transactionManager()
        throws InstantiationException, IllegalAccessException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public UserService userService() throws InstantiationException, IllegalAccessException {
        UserService userService = new UserService();
        userService.setUserDao(userDao());
        userService.setTransactionManager(transactionManager());
        userService.setMailSender(mailSender());
        return userService;
    }

    @Bean
    public UserDao userDao() throws InstantiationException, IllegalAccessException {
        UserDaoJdbc dao = new UserDaoJdbc();
        dao.setDatasource(dataSource());
        return dao;
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
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
