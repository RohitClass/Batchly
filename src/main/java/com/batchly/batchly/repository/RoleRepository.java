package com.batchly.batchly.repository;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.tree.RowMapper;

import org.hibernate.sql.Delete;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.batchly.batchly.entity.Role;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepository{
    private final JdbcTemplate jdbc;
    
    public Optional<Role> findById(Long id){
        try{
            Role role =jdbc.queryForObject("SELECT id,name,description FROM roles WHERE id=?", roleRowMapper(),id);
            return Optional.ofNullable(role);
        }
        catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    public Optional<Role> findByName(String name){
        try{
            Role role=jdbc.queryForObject("SLECT id,name,description FROM roles WHERE name=?", roleRowMapper(),name);
            return Optional.ofNullable(role);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    public List<Role> findAll(){
        return jdbc.query("SELECT id, name,description FROM roles",roleRowMapper());
    }
    public Role save(String name , String description){
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update(con->{
            PreparedStatement ps = con.prepareStatement("INSERT INTO roles (name,description) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, description);
            return ps;
        },key);
        return findById(Objects.requireNonNull(key.getKey()).longValue()).orElseThrow();
    }   
    public void deleteById(Long id){
        jdbc.update("DELETE FROM roles WHERE id =?", id);
    }
    private RowMapper<Role> roleRowMapper(){
        return (rs ,rowNum)->{
            Role r =  new Role();
            r.setId(rs.getLong("id"));
            r.setName(rs.getString("name"));
            r.setDescription(rs.getString("description"));
            return r;
        };
    }
    
}