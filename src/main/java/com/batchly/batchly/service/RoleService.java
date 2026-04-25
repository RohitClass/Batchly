package com.batchly.batchly.service;

import com.batchly.batchly.entity.Role;
import com.batchly.batchly.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;

    public List<Role> getAll() {
        return roleRepo.findAll();
    }

    public Optional<Role> getById(Long id) {
        return roleRepo.findById(id);
    }

    public Role create(String name, String description) {
        return roleRepo.save(name.toUpperCase(), description);
    }

    public Role update(Long id, String name, String description) {
        return roleRepo.update(id, name.toUpperCase(), description);
    }

    public void delete(Long id) {
        roleRepo.deleteById(id);
    }
}