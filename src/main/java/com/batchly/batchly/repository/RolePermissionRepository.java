package com.batchly.batchly.repository;

import com.batchly.batchly.entity.RolePermission;

import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RolePermissionRepository {

    private final JdbcTemplate jdbc;

    // RowMapper defined as a field — fixes "permRowMapper() is undefined"
    @NonNull
    private final RowMapper<RolePermission> permRowMapper = (rs, rowNum) -> {
        RolePermission p = new RolePermission();
        p.setId(rs.getLong("id"));
        p.setRoleId(rs.getLong("role_id"));
        p.setModuleName(rs.getString("module_name"));
        p.setCanCreate(rs.getBoolean("can_create"));
        p.setCanRead(rs.getBoolean("can_read"));
        p.setCanUpdate(rs.getBoolean("can_update"));
        p.setCanDelete(rs.getBoolean("can_delete"));
        return p;
    };

    public List<RolePermission> findByRoleId(Long roleId) {
        String sql = "SELECT rp.id, rp.role_id, rp.module_name, " +
                     "rp.can_create, rp.can_read, rp.can_update, rp.can_delete " +
                     "FROM role_permissions rp " +
                     "JOIN roles r ON rp.role_id = r.id " +
                     "WHERE rp.role_id = ?";
        return jdbc.query(sql, permRowMapper, roleId);
    }

    public Optional<RolePermission> findByRoleIdAndModule(Long roleId, String moduleName) {
        String sql = "SELECT rp.id, rp.role_id, rp.module_name, " +
                     "rp.can_create, rp.can_read, rp.can_update, rp.can_delete " +
                     "FROM role_permissions rp " +
                     "JOIN roles r ON rp.role_id = r.id " +
                     "WHERE rp.role_id = ? AND rp.module_name = ?";
        try {
            RolePermission p = jdbc.queryForObject(sql, permRowMapper, roleId, moduleName);
            return Optional.ofNullable(p);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public RolePermission upsert(RolePermission p) {
        Optional<RolePermission> existing =
            findByRoleIdAndModule(p.getRoleId(), p.getModuleName());

        if (existing.isPresent()) {
            jdbc.update(
                "UPDATE role_permissions SET can_create=?, can_read=?, can_update=?, can_delete=? " +
                "WHERE role_id=? AND module_name=?",
                p.isCanCreate(), p.isCanRead(), p.isCanUpdate(), p.isCanDelete(),
                p.getRoleId(), p.getModuleName()
            );
            return findByRoleIdAndModule(p.getRoleId(), p.getModuleName()).orElseThrow();
        } else {
            KeyHolder key = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO role_permissions " +
                    "(role_id, module_name, can_create, can_read, can_update, can_delete) " +
                    "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS  // fixes "RETURN_GENERATED_KEYS cannot be resolved"
                );
                ps.setLong(1, p.getRoleId());
                ps.setString(2, p.getModuleName());
                ps.setBoolean(3, p.isCanCreate());
                ps.setBoolean(4, p.isCanRead());
                ps.setBoolean(5, p.isCanUpdate());
                ps.setBoolean(6, p.isCanDelete());
                return ps;
            }, key);
            p.setId(Objects.requireNonNull(key.getKey()).longValue());
            return p;
        }
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM role_permissions WHERE id = ?", id);
    }
}