package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.ApiResponse;
import com.backend.VNPT_Intern_Project.dtos.permission.PermissionDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.permission.PermissionSetRequest;
import com.backend.VNPT_Intern_Project.dtos.role.RoleDTOResponse;
import com.backend.VNPT_Intern_Project.services.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<PermissionDTOResponse> permissionDTOResponses = permissionService.getAll();
        ApiResponse<List<PermissionDTOResponse>> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", permissionDTOResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createPermission(@RequestBody String name) {
        PermissionDTOResponse permissionDTOResponse = permissionService.create(name);
        ApiResponse<PermissionDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", permissionDTOResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/setForRole")
    public ResponseEntity<?> setPermission(@RequestBody PermissionSetRequest request) {
        RoleDTOResponse roleDTOResponse = permissionService.setPermission(request.getPermissionName(), request.getRoleName());
        ApiResponse<RoleDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", roleDTOResponse);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuidPermission}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuidPermission) {
        PermissionDTOResponse permissionDTOResponse = permissionService.delete(uuidPermission);
        ApiResponse<PermissionDTOResponse> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "SUCCESS", permissionDTOResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}












