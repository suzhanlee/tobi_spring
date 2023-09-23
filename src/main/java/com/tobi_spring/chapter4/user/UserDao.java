package com.tobi_spring.chapter4.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao() {
    }

    public void setDatasource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 인스턴스 변수
    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    public void add(User user) throws DuplicateUserIdException, SQLException {
//        try {
        jdbcTemplate.update("insert into users(id, name, password) values (?,?,?)",
            user.getId(), user.getName(), user.getPassword());
//        } catch (SQLException e) {
//            // ERRORCode가 MYSQL의 "Duplicate Entry(1062)" 면 예외 전환
//            if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
//                throw new DuplicateUserIdException(e);
//            } else {
//                throw new RuntimeException(e);
//            }
//        }
    }

    public void deleteAll() {
//        jdbcTemplate.update(
//            new PreparedStatementCreator() {
//                @Override
//                public PreparedStatement createPreparedStatement(Connection con)
//                    throws SQLException {
//                    return con.prepareStatement("delete from users");
//                }
//            }
//        );

        // jdbcTemplate 의 내장 콜백을 사용하는 메소드를 호출
        jdbcTemplate.update("delete from users");
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
            new Object[]{id}, userMapper);
    }

    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users order by id", userMapper);
    }
}
