package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.authentication.AuthenticationRequest;
import com.backend.VNPT_Intern_Project.dtos.authentication.AuthenticationResponse;
import com.backend.VNPT_Intern_Project.dtos.introspect.IntrospectRequest;
import com.backend.VNPT_Intern_Project.dtos.introspect.IntrospectResponse;
import com.backend.VNPT_Intern_Project.entities.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface IAuthService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    AuthenticationResponse authenticate(AuthenticationRequest request);
    String generateToken(User user);
}
