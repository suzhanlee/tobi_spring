package com.tobi_spring.chapter6.proxyfactorybean;

import static com.tobi_spring.chapter6.proxyfactorybean.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.tobi_spring.chapter6.proxyfactorybean.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(DaoFactory.class)
class UserServiceTest {

    @Autowired
    MailSender mailSender;

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;
    List<User> users;

    @Autowired
    UserDao userDao;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
            new User("bumjin", "박범진", "p1", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER - 1, 0,
                "email1@email.com"),
            new User("joytouch", "강명성", "p2", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0,
                "email2@email.com"),
            new User("erwins", "신승한", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1,
                "email3@email.com"),
            new User("madnite1", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD,
                "email4@email.com"),
            new User("green", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE, "email5@email.com")
        );
    }

    static class TestUserService extends UserServiceImpl {

        private String id;

        public TestUserService(String id) {
            this.id = id;
        }

        public void upgradeLevel(User user) {
            if (user.getId().equals(id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {

    }

    @Test
    @DirtiesContext
    void upgradeAllOrNothing() throws Exception {
        TestUserService testUserService = new TestUserService(users.get(3).getId());

        testUserService.setUserDao(userDao);
        testUserService.setMailSender(mailSender);


        ProxyFactoryBean txProxyFactoryBean = context.getBean("&userService",
            ProxyFactoryBean.class);
        txProxyFactoryBean.setTarget(testUserService);

        UserService userServiceTx = (UserService) txProxyFactoryBean.getObject();

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            assertThrows(TestUserServiceException.class, () -> userServiceTx.upgradeLevels());

        } catch (TestUserServiceException e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        // 테스트 대역이 아닌, 실제 DB에서 데이터를 가져온다.
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
        } else {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
        }
    }

}