package com.batchly.batchly.repository;

import com.batchly.batchly.entity.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    // Explicit constructor — fixes "blank final field jdbc not initialized"
    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String FIND_WITH_EMAIL = "SELECT * FROM users WHERE email=?";
    private static final String FIND_WITH_PHONE = "SELECT * FROM users WHERE phone_no=?";
    private static final String FIND_WITH_USER_NAME = "SELECT * FROM users WHERE user_name=?";

    public Optional<User> findByEmailIgnoreCase(String email) {
        List<Map<String, Object>> rows = jdbc.queryForList(FIND_WITH_EMAIL, email);
        if (rows.isEmpty())
            return Optional.empty();
        return Optional.of(mapRows(rows));
    }

    public Optional<User> findByPhoneNo(String email) {
        List<Map<String, Object>> rows = jdbc.queryForList(FIND_WITH_PHONE, email);
        
        if (rows.isEmpty())
            return Optional.empty();
        return Optional.of(mapRows(rows));
    }

    public Optional<User> findByUserName(String email) {
        List<Map<String, Object>> rows = jdbc.queryForList(FIND_WITH_USER_NAME, email);
        if (rows.isEmpty())
            return Optional.empty();
        return Optional.of(mapRows(rows));
    }

    private User mapRows(List<Map<String, Object>> rows) {

        // ✅ Handle empty or null result
        if (rows == null || rows.isEmpty()) {
            return null; // or throw exception if you prefer
        }

        Map<String, Object> first = rows.get(0);

        User u = new User();

        u.setId(toLong(first.get("id")));
        u.setEmail((String) first.get("email"));
        u.setPhoneNo((String) first.get("phone_no"));
        u.setUserName((String) first.get("user_name"));
        u.setToken((String) first.get("token"));
        u.setPassword((String) first.get("password"));

        return u;
    }

    private Long toLong(Object val) {
        if (val == null)
            return null;

        if (val instanceof Number) {
            return ((Number) val).longValue();
        }

        return Long.parseLong(val.toString());
    }

    public void save(String email, String token) {

        String sql = "UPDATE users SET token=? WHERE email=?";

        jdbc.update(sql, token, email);
    }
}