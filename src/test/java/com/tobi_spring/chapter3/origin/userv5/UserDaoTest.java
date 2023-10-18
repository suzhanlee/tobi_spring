package com.tobi_spring.chapter3.origin.userv5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tobi_spring.chapter3.origin.userv5.DaoFactory;
import com.tobi_spring.chapter3.origin.userv5.User;
import com.tobi_spring.chapter3.origin.userv5.UserDao;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = DaoFactory.class)
class UserDaoTest {

    // 스프링 테스트 컨텍스트 프레임워크로 테스트에서 동일한 설정 파일 사용
    @Autowired
    private ApplicationContext context;
    private UserDao dao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {

        dao = context.getBean("userDao", UserDao.class);

        user1 = new User("gyumee", "박성철", "spring01");
        user2 = new User("leegw700", "이길원", "spring02");
        user3 = new User("bumjin", "박범진", "spring03");

        /**
         * 정리
         * 지금까지 실험을 해보니 dao에서 JdbcContext를 가져오면, 싱글톤이 보장된다.
         * 그러나 문제는 dao를 통해서가 아닌 ApplicationContext에서 jdbcContext를 가져오면
         * 당연히 빈 등록이 되어 있지 않아, 스프링에서 제공하는 DI를 사용할 수 없다.
         * 문제는 여기서 발생한다. 결국 다른곳에서 jdbcContext 자체를 빈으로 사용할 수는 없다.
         * 스프링이 제공하는 DI를 위해서는 주입되는 오브젝트와 주입받는 오브젝트 양쪽 모두 스프링 빈으로
         * 등록되어 있어야 한다. 즉, jdbcContext는 다른 빈을 DI 받기 위해서라도 스프링 빈으로 등록돼야 한다.
         */
        System.out.println(dao.getJdbcContext());
//        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//        Object jdbcContext = context.getBean("jdbcContext");
//        System.out.println(jdbcContext);

        // context에서 가져온 userDao의 참조값은 항상 동일!
        Object userDao = context.getBean("userDao");
        System.out.println(userDao);

        Object dataSource = context.getBean("dataSource");
        System.out.println(dataSource);
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        User userget1 = dao.get(user1.getId());
        assertThat(userget1.getName()).isEqualTo(user1.getName());
        assertThat(userget1.getPassword()).isEqualTo(user1.getPassword());

        User userget2 = dao.get(user2.getId());
        assertThat(userget2.getName()).isEqualTo(user2.getName());
        assertThat(userget2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

        dao.add(user1);
        assertThat(dao.getCount()).isEqualTo(1);

        dao.add(user2);
        assertThat(dao.getCount()).isEqualTo(2);

        dao.add(user3);
        assertThat(dao.getCount()).isEqualTo(3);
    }

    @Test
    void getUserFailure() throws SQLException {
        dao.deleteAll();

        assertThat(dao.getCount()).isEqualTo(0);

        assertThrows(EmptyResultDataAccessException.class, () -> dao.get("unknown_id"));
    }

}