package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.permission.PermissionDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.role.RoleDTOResponse;
import com.backend.VNPT_Intern_Project.entities.Permission;
import com.backend.VNPT_Intern_Project.entities.Role;
import com.backend.VNPT_Intern_Project.entities.User;
import com.backend.VNPT_Intern_Project.exception.ConflictException;
import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
import com.backend.VNPT_Intern_Project.repositories.PermissionRepository;
import com.backend.VNPT_Intern_Project.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PermissionService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;


    public List<PermissionDTOResponse> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissions
                .stream()
                .map(permission -> new PermissionDTOResponse(permission.getUuidPermission(), permission.getName()))
                .toList();
    }

    @Transactional
    public PermissionDTOResponse create(String permissionName) {
        permissionRepository.findByName(permissionName)
                .ifPresent(permission -> {
                    throw new ConflictException("Permission is already exist");
                });

        Permission permission = new Permission();
        permission.setName(permissionName);

        permission = permissionRepository.save(permission);

        return new PermissionDTOResponse(permission.getUuidPermission(), permissionName);
    }

    @Transactional
    public RoleDTOResponse setPermission(String permissionName, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role is not exist"));

        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new ResourceNotFoundException("Permission is not exist, please create new permission!"));

        if (role.getPermissionSet().contains(permission)) {
            throw new ConflictException("Permission was already set");
        }

        role.getPermissionSet().add(permission);
        roleRepository.save(role);

        return new RoleDTOResponse(role.getUuidRole(), roleName,
                role.getPermissionSet().stream()
                        .map(Permission::getName)
                        .collect(Collectors.toSet()));
    }

    @Transactional
    public PermissionDTOResponse delete(String permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Permission is not exist"));

        permissionRepository.delete(permission);
        return new PermissionDTOResponse(permission.getUuidPermission(), permission.getName());
    }

}

