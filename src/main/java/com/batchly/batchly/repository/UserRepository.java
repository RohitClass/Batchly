package com.batchly.batchly.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;

import com.batchly.batchly.dto.UserWithPermissions;
import com.batchly.batchly.entity.RolePermission;

public class UserRepository {
    private final JdbcTemplate jdbc;
    private static final String FIND_WITH_PERMS = "SELECT u.id AS user_id, u.email, u.password, u.role_id, " +
            "       r.name AS role_name, " +
            "       rp.id AS perm_id, rp.module_name, " +
            "       rp.can_create, rp.can_read, rp.can_update, rp.can_delete " +
            "FROM users u " +
            "JOIN roles r ON u.role_id = r.id " +
            "LEFT JOIN role_permissions rp ON r.id = rp.role_id " +
            "WHERE u.email = ?";
    public Optional<UserWithPermissions> findByEmailWithPermissions(String email) {
        List<Map<String, Object>> rows = jdbc.queryForList(FIND_WITH_PERMS, email);
        if (rows.isEmpty())
            return Optional.empty();
        return Optional.of(mapRows(rows));
    }

    private UserWithPermissions mapRows(List<Map<String, Object>> rows) {
        Map<String, Object> first = rows.get(0);
        UserWithPermissions u = new UserWithPermissions();
        u.setUserId(toLong(first.get("user_id")));
        u.setEmail((String) first.get("email"));
        u.setPassword((String) first.get("password"));
        u.setRoleId(toLong(first.get("role_id")));
        u.setRoleName((String) first.get("role_name"));
        rows.forEach(row-> {
            if(row.get("perm_id")!= null){
                RolePermission p = new RolePermission();
                p.setId(toLong(row.get("perm_id")));
                p.setRoleId(toLong(row.get("role_id")));
                p.setModuleName((String) row.get("module_name"));
                p.setCanCreate(toBoolean(row.get("can_create")));
                p.setCanRead(toBoolean(row.get("can_read")));
                p.setCanUpdate(toBoolean(row.get("can_update")));
                p.setCanDelete(toBoolean(row.get("can_delete")));
                u.getPermissions().add(p);
            }
        });
        return u;
    }
    public void save(String email, String encodedPassword, Long roleId) {
        jdbc.update(
            "INSERT INTO users (email, password, role_id) VALUES (?, ?, ?)",
            email, encodedPassword, roleId
        );
    }
     public boolean existsByEmail(String email) {
        Integer count = jdbc.queryForObject(
            "SELECT COUNT(*) FROM users WHERE email = ?", Integer.class, email
        );
        return count != null && count > 0;
    }

    private Long toLong(Object val) {
        return val == null ? null : ((Number) val).longValue();
    }

    private boolean toBoolean(Object val) {
        if (val == null) return false;
        if (val instanceof Boolean) return (Boolean) val;
        if (val instanceof Number) return ((Number) val).intValue() == 1;
        return false;
    }
}
