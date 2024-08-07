package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.ApiResponse;
import com.backend.VNPT_Intern_Project.dtos.user.request.CreateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.UpdateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.UserAddressDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.response.UserAddressDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.user.response.UserDTOResponse;
import com.backend.VNPT_Intern_Project.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        List<UserDTOResponse> userList = userService.getAllUsers();
        ApiResponse<List<UserDTOResponse>> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", userList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{uuid_user}")
    public ResponseEntity<?> getUserById(@PathVariable String uuid_user) {
        UserDTOResponse user = userService.getUserById(uuid_user);
        ApiResponse<UserDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/myInfo")
    public ResponseEntity<?> getMyInfo() {
        UserDTOResponse user = userService.getMyInfo();
        ApiResponse<UserDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody @Valid CreateUserDTORequest request) {
        UserDTOResponse user = userService.createUser(request);
        ApiResponse<UserDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{uuidUser}")
    public ResponseEntity<?> updateUser(@PathVariable String uuidUser, @RequestBody @Valid UpdateUserDTORequest request) {
        UserDTOResponse user = userService.updateUser(uuidUser, request);
        ApiResponse<UserDTOResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{uuidUser}")
    public ResponseEntity<?> deleteUser(@PathVariable String uuidUser) {
        UserDTOResponse user = userService.deleteUser(uuidUser);
        ApiResponse<UserDTOResponse> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "SUCCESS", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addresses/{uuidUser}")
    public ResponseEntity<?> addNewAddress(@PathVariable String uuidUser, @RequestBody @Valid UserAddressDTORequest request) {
        UserAddressDTOResponse userAddress = userService.addAddress(uuidUser, request);
        ApiResponse<UserAddressDTOResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", userAddress);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}












