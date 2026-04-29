// package com.batchly.batchly.repository;

// import com.batchly.batchly.entity.Role;

// import org.springframework.lang.NonNull;
// import lombok.RequiredArgsConstructor;
// import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.RowMapper;
// import org.springframework.jdbc.support.GeneratedKeyHolder;
// import org.springframework.jdbc.support.KeyHolder;
// import org.springframework.stereotype.Repository;

// import java.sql.PreparedStatement;
// import java.sql.Statement;
// import java.util.List;
// import java.util.Objects;
// import java.util.Optional;

// @Repository
// @RequiredArgsConstructor
// public class RoleRepository {

//     private final JdbcTemplate jdbc;

//     // RowMapper defined as a field — fixes "roleRowMapper() is undefined"
//     @NonNull
//     private final RowMapper<Role> roleRowMapper = (rs, rowNum) -> {
//         Role r = new Role();
//         r.setId(rs.getLong("id"));
//         r.setName(rs.getString("name"));
//         r.setDescription(rs.getString("description"));
//         return r;
//     };

//     public Optional<Role> findById(Long id) {
//         try {
//             Role role = jdbc.queryForObject(
//                 "SELECT id, name, description FROM roles WHERE id = ?",
//                 roleRowMapper, id
//             );
//             return Optional.ofNullable(role);
//         } catch (EmptyResultDataAccessException e) {
//             return Optional.empty();
//         }
//     }

//     public Optional<Role> findByName(String name) {
//         try {
//             Role role = jdbc.queryForObject(
//                 "SELECT id, name, description FROM roles WHERE name = ?",
//                 roleRowMapper, name
//             );
//             return Optional.ofNullable(role);
//         } catch (EmptyResultDataAccessException e) {
//             return Optional.empty();
//         }
//     }

//     public List<Role> findAll() {
//         return jdbc.query(
//             "SELECT id, name, description FROM roles",
//             roleRowMapper
//         );
//     }

//     public Role save(String name, String description) {
//         KeyHolder key = new GeneratedKeyHolder();
//         jdbc.update(con -> {
//             PreparedStatement ps = con.prepareStatement(
//                 "INSERT INTO roles (name, description) VALUES (?, ?)",
//                 Statement.RETURN_GENERATED_KEYS  // fixes "RETURN_GENERATED_KEYS cannot be resolved"
//             );
//             ps.setString(1, name);
//             ps.setString(2, description);
//             return ps;
//         }, key);
//         return findById(Objects.requireNonNull(key.getKey()).longValue()).orElseThrow();
//     }

//     // fixes "update(Long, String, String) is undefined for RoleRepository"
//     public Role update(Long id, String name, String description) {
//         jdbc.update(
//             "UPDATE roles SET name = ?, description = ? WHERE id = ?",
//             name, description, id
//         );
//         return findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
//     }

//     public void deleteById(Long id) {
//         jdbc.update("DELETE FROM roles WHERE id = ?", id);
//     }
// }