package com.batchly.batchly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @GetMapping
    @PreAuthorize("hasAuthority('attendance:READ')")
    public ResponseEntity<String> getAll() {
        return ResponseEntity.ok("All attendance records");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('attendance:CREATE')")
    public ResponseEntity<String> mark() {
        return ResponseEntity.ok("Attendance marked");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('attendance:UPDATE')")
    public ResponseEntity<String> update(@PathVariable Long id) {
        return ResponseEntity.ok("Attendance updated: " + id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('attendance:DELETE')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok("Attendance deleted: " + id);
    }

}