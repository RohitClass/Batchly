package com.batchly.batchly.repository;

import java.beans.Statement;
import java.lang.StackWalker.Option;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.batchly.batchly.entity.RolePermission;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RolePermissionRepository {
    private final JdbcTemplate jdbc;
    public List<RolePermission> findById(Long id){
        return jdbc.query("SELECT rp.id,rp.role_id,rp.module_name,rp.can_read,rp.can_create,rp.can_update,rp.can_delete FROM role_permission rp JOIN roles r ON rp.role_id=r.id WHERE rp.role_id=?", permRowMapper(),roleId);
    }
    public Optional<RolePermission> findByRoleIdAndModule(Long id , String module ){
        try{
            RolePermission p = jdbc.queryForObject("SELECT rp.id,rp.role_id,rp.module_name,rp.can_read,rp.can_create,rp.can_update,rp.can_delete FROM role_permission rp JOIN roles r ON rp.role_id=r.id WHERE rp.role_id=? AND rp.module_name=?",permRowMapper(),id,module);
            return Optional.ofNullable(p);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    public RolePermission upsert(RolePermission p){
        Optional<RolePermission> existing = findByRoleIdAndModule(p.getRoleID(), p.getModuleName());
         if (existing.isPresent()) {
            jdbc.update(
                "UPDATE role_permissions SET can_create=?, can_read=?, can_update=?, can_delete=? " +
                "WHERE role_id=? AND module_name=?",
                p.isCanCreate(), p.isCanRead(), p.isCanUpdate(), p.isCanDelete(),
                p.getRoleID(), p.getModuleName()
            );
            return findByRoleIdAndModule(p.getRoleId(), p.getModuleName()).orElseThrow();
        } else {
            KeyHolder key = new GeneratedKeyHolder();
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO role_permissions " +
                    "(role_id, module_name, can_create, can_read, can_update, can_delete) " +
                    "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
                );
                ps.setLong(1, p.getRoleID());
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
    
}
