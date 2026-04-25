package com.batchly.batchly.controller;

import com.batchly.batchly.dto.PermissionRequest;
import com.batchly.batchly.entity.RolePermission;
import com.batchly.batchly.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/permissions")
@RequiredArgsConstructor
public class RolePermissionController {

    private final RolePermissionService service;

    @GetMapping("/role/{roleId}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<List<RolePermission>> getByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(service.getByRole(roleId));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<RolePermission> upsert(@RequestBody PermissionRequest req) {
        return ResponseEntity.ok(service.upsert(req));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Permission removed successfully");
    }
}