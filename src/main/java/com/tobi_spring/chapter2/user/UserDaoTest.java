package com.tobi_spring.chapter2.user;

import java.sql.SQLException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // dao가 어떻게 만들어지고, 어떻게 초기화되는지 신경 쓰지 않고 팩토리에게 책임을 위임하면 된다.
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        // 제네릭 메소드 사용으로 타입 캐스팅 과정 스킵
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }
}
