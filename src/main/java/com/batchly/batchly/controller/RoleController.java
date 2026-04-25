package com.batchly.batchly.controller;

import com.batchly.batchly.entity.Role;
import com.batchly.batchly.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
            roleService.getById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"))
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Role> create(@RequestBody Role req) {
        return ResponseEntity.ok(roleService.create(req.getName(), req.getDescription()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role req) {
        return ResponseEntity.ok(roleService.update(id, req.getName(), req.getDescription()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.ok("Role deleted successfully");
    }
}