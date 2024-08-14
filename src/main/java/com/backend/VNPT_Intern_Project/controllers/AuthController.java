package com.backend.VNPT_Intern_Project.controllers;

import com.backend.VNPT_Intern_Project.dtos.ApiResponse;
import com.backend.VNPT_Intern_Project.dtos.authentication.AuthenticationRequest;
import com.backend.VNPT_Intern_Project.dtos.authentication.AuthenticationResponse;
import com.backend.VNPT_Intern_Project.dtos.authentication.LogoutRequest;
import com.backend.VNPT_Intern_Project.dtos.authentication.RefreshRequest;
import com.backend.VNPT_Intern_Project.dtos.introspect.IntrospectRequest;
import com.backend.VNPT_Intern_Project.dtos.introspect.IntrospectResponse;
import com.backend.VNPT_Intern_Project.services.AuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse authResponse = authService.authenticate(request);

        ApiResponse<AuthenticationResponse> response =
                new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", authResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        ApiResponse<?> response =
                new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", null);
        authService.logout(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid RefreshRequest request) throws ParseException, JOSEException {
        AuthenticationResponse authResponse = authService.refreshToken(request);

        ApiResponse<AuthenticationResponse> response =
                new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", authResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/introspect")
    public ResponseEntity<?> introspect(@RequestBody @Valid IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse introspectResponse = authService.introspect(request);

        ApiResponse<IntrospectResponse> response =
                new ApiResponse<>(HttpStatus.CREATED.value(), "SUCCESS", introspectResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
