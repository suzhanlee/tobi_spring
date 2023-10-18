package com.tobi_spring.chapter3.origin.userv6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {

    private JdbcContext jdbcContext;

    public UserDao() {
    }

    public void setDatasource(DataSource dataSource) {
        jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);
    }

    // Client
    public void add(User user) throws ClassNotFoundException, SQLException {

        // Strategy Class 생성 -> 기존에는 아래와 같이 익명 클래스로 CallBack Object를 만든다.
        class AddStatement implements StatementStrategy {

            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(
                    "insert into users(id, name, password) values (?, ?, ?)"
                );
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        }

        StatementStrategy st = new AddStatement();

        // template(context)에게 전략 위임
        // Client가 template을 호출하면서 CallBack Object를 전달 = 메소드 레벨에서 일어나는 DI
        jdbcContext.workWithStatementStrategy(st);

        //========== 중복 코드 =============

        // Client가 template 기능을 호출하는 것과 template이 사용할 CallBack 인터페이스를 구현한 오브젝트를
        // 메소드를 통해 주입해주는 DI작업과 동시에 일어난다.
        // Template
        jdbcContext.workWithStatementStrategy((c) -> {
                // CallBack
                // Client가 template을 호출하면서 CallBack Object를 전달 = 메소드 레벨에서 일어나는 DI
                PreparedStatement ps = c.prepareStatement(
                    "insert into users(id, name, password) values (?, ?, ?)"
                );
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        );
    }

    public void deleteAll() throws SQLException {
        jdbcContext.workWithStatementStrategy((c) ->
            c.prepareStatement("delete from users")
        );
    }

    public User get(String id)
        throws ClassNotFoundException, SQLException {
        Connection c = jdbcContext.getDataSource().getConnection();

        PreparedStatement ps = c.prepareStatement(
            "select * from users where id = ?"
        );

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return user;
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = jdbcContext.getDataSource().getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();

            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
