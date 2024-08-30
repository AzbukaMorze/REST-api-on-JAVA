package com.mails_rest_api.demo;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersRestController implements RowMapper<UsersRestController.User> {

    private final NamedParameterJdbcOperations jdbcOperations;

    public UsersRestController(final NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    record User(int id, String firstName, String lastName) {

    }

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }

    @GetMapping
    public List<User> getAllUsers() {
        return jdbcOperations.query("SELECT * FROM users", this);
    }

}
