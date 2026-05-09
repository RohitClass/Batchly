package com.batchly.batchly.repository;

import com.batchly.batchly.security.CoachingDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CoachingRepository {

    private final JdbcTemplate jdbc;

    public Optional<CoachingDetails> findByUserId(Long userId) {

        String sql = """
                SELECT
                    u.id AS user_id,
                    u.email,
                    u.user_name,
                    u.phone_no,
                    u.password,
                    u.role AS role_name,
                    c.id AS coaching_id
                    FROM users u JOIN coachings c
                    ON u.id = c.user_id
                WHERE u.id = ?
                """;

        try {

            return jdbc.query(
                    sql,
                    ps -> ps.setLong(1, userId),
                    (rs, rowNum) -> {

                        CoachingDetails coaching = new CoachingDetails();

                        coaching.setUserId(rs.getLong("user_id"));
                        coaching.setEmail(rs.getString("email"));
                        coaching.setUserName(rs.getString("user_name"));
                        coaching.setPhoneNo(rs.getString("phone_no"));
                        coaching.setPassword(rs.getString("password"));
                        coaching.setRole(rs.getString("role_name"));

                        // coaching_id can be null because of LEFT JOIN
                        Object coachingId = rs.getObject("coaching_id");

                        if (coachingId != null) {
                            coaching.setId(((Number) coachingId).longValue());
                        }

                        coaching.setAuthorities(
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + rs.getString("role_name")
                                        )
                                )
                        );

                        return coaching;
                    }

            ).stream().findFirst();

        } catch (Exception e) {

            System.out.println("========== SQL ERROR ==========");
            e.printStackTrace();
            System.out.println("================================");

            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }
}