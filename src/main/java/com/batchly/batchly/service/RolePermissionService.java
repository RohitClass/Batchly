// package com.batchly.batchly.service;

// import com.batchly.batchly.dto.PermissionRequest;
// import com.batchly.batchly.entity.RolePermission;
// import com.batchly.batchly.repository.RolePermissionRepository;
// import com.batchly.batchly.repository.RoleRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// @RequiredArgsConstructor
// public class RolePermissionService {

//     private final RolePermissionRepository permRepo;
//     private final RoleRepository roleRepo;

//     public List<RolePermission> getByRole(Long roleId) {
//         return permRepo.findByRoleId(roleId);
//     }

//     public RolePermission upsert(PermissionRequest req) {
//         roleRepo.findById(req.getRoleId())
//             .orElseThrow(() -> new RuntimeException("Role not found"));

//         RolePermission p = new RolePermission();
//         p.setRoleId(req.getRoleId());
//         p.setModuleName(req.getModuleName());
//         p.setCanCreate(req.isCanCreate());
//         p.setCanRead(req.isCanRead());
//         p.setCanUpdate(req.isCanUpdate());
//         p.setCanDelete(req.isCanDelete());

//         return permRepo.upsert(p);
//     }

//     public void delete(Long id) {
//         permRepo.deleteById(id);
//     }
// }