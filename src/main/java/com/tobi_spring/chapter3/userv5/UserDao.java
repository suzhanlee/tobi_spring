package com.tobi_spring.chapter3.userv5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.Getter;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {

    @Getter
    private JdbcContext jdbcContext;

    public UserDao() {
    }

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

        jdbcContext.workWithStatementStrategy((c) -> {
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
