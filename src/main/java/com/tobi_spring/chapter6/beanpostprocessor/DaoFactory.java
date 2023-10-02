package com.tobi_spring.chapter6.beanpostprocessor;

import com.mysql.cj.jdbc.Driver;
import javax.sql.DataSource;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
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

//    @Bean
//    public NameMatchMethodPointcut transactionPointcut() {
//        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
//        pointcut.setMappedName("upgrade*");
//        return pointcut;
//    }

    /**
     * 이제는 이전 ProxyFactoryBean에서 했던 Advisor를 DI 해주는 부분은 어디에서도 찾을 수 없다.
     * 대신 Advisor를 이용하는 자동 프록시 생성기인 DefaultAdvisorAutoProxyCreator 에 의해 자동 수집되고,
     * 프록시 대상 선정 과정에 참여하며, 자동 생성된 프록시에 다이내믹하게 DI 돼서 동작하는
     * Advisor가 된다.
     */
    @Bean
    public NameMatchClassMethodPointcut transactionPointcut() {
        NameMatchClassMethodPointcut classMethodPointcut = new NameMatchClassMethodPointcut();
        classMethodPointcut.setMappedClassName("*ServiceImpl"); // 사실상 이 부분이 target 이다!
        classMethodPointcut.setMappedName("upgrade*");
        return classMethodPointcut;
    }
    @Bean
    public DefaultPointcutAdvisor transactionAdvisor()
        throws InstantiationException, IllegalAccessException {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator autoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
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
    public UserService userServiceImpl() throws InstantiationException, IllegalAccessException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao());
        userService.setMailSender(mailSender());
        return userService;
    }
    @Bean
    public UserService testUserServiceImpl() throws InstantiationException, IllegalAccessException {
        TestUserServiceImpl testUserService = new TestUserServiceImpl();
        testUserService.setUserDao(userDao());
        testUserService.setMailSender(mailSender());
        return testUserService;
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
