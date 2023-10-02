package com.tobi_spring.chapter6.proxyfactorybean;

import com.mysql.cj.jdbc.Driver;
import com.tobi_spring.chapter6.dynamicproxyfactorybean.TxProxyFactoryBean;
import com.tobi_spring.chapter6.dynamicproxyfactorybean.UserService;
import javax.sql.DataSource;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DaoFactory {

    @Bean
    public TransactionAdvice transactionAdvice()
        throws InstantiationException, IllegalAccessException {
        TransactionAdvice transactionAdvice = new TransactionAdvice();
        transactionAdvice.setTransactionManager(transactionManager());
        return transactionAdvice;
    }

    @Bean
    public NameMatchMethodPointcut transactionPointcut() {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("upgrade*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor transactionAdvisor()
        throws InstantiationException, IllegalAccessException {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public ProxyFactoryBean userService() throws InstantiationException, IllegalAccessException {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(userServiceImpl());
        proxyFactoryBean.setInterceptorNames("transactionAdvisor");
        return proxyFactoryBean;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }

    @Bean
    public PlatformTransactionManager transactionManager()
        throws InstantiationException, IllegalAccessException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public UserServiceImpl userServiceImpl() throws InstantiationException, IllegalAccessException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
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
