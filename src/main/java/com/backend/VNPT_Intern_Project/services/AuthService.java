package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.authentication.AuthenticationRequest;
import com.backend.VNPT_Intern_Project.dtos.authentication.AuthenticationResponse;
import com.backend.VNPT_Intern_Project.dtos.introspect.IntrospectRequest;
import com.backend.VNPT_Intern_Project.dtos.introspect.IntrospectResponse;
import com.backend.VNPT_Intern_Project.entities.Permission;
import com.backend.VNPT_Intern_Project.entities.Role;
import com.backend.VNPT_Intern_Project.entities.User;
import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
import com.backend.VNPT_Intern_Project.repositories.PermissionRepository;
import com.backend.VNPT_Intern_Project.repositories.RoleRepository;
import com.backend.VNPT_Intern_Project.repositories.UserRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IAuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor

public class AuthService implements IAuthService {
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.secretKey}")
    protected String SIGNER_KEY;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isVerified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(isVerified && expiredTime.after(new Date()))
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AuthenticationException("Unauthenticated") {};
        }
        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // Tao access_token
    @Override
    public String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("quan.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        Set<Role> roleSet = roleRepository.findRolesByUserUuid(user.getUuidUser());

        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!roleSet.isEmpty()) {
            roleSet.forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                Set<Permission> permissions = permissionRepository.findPermissionsByRoleName(role.getName());
                if (!CollectionUtils.isEmpty(permissions)) {
                    permissions.forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }

}
