package com.batchly.batchly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @GetMapping
    @PreAuthorize("hasAuthority('grades:READ')")
    public ResponseEntity<String> getAll() {
        return ResponseEntity.ok("All grades fetched");
    }

    @PostMapping
    @PreAuthorize("hasAuthority('grades:CREATE')")
    public ResponseEntity<String> create() {
        return ResponseEntity.ok("Grade created");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('grades:UPDATE')")
    public ResponseEntity<String> update(@PathVariable Long id) {
        return ResponseEntity.ok("Grade updated: " + id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('grades:DELETE')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok("Grade deleted: " + id);
    }
}