package com.backend.VNPT_Intern_Project.configuration;

import com.backend.VNPT_Intern_Project.entities.Role;
import com.backend.VNPT_Intern_Project.entities.User;
import com.backend.VNPT_Intern_Project.repositories.RoleRepository;
import com.backend.VNPT_Intern_Project.repositories.UserRepository;
import com.backend.VNPT_Intern_Project.utils.RoleConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                Set<Role> roleSet = new HashSet<>();

                Role userRole = roleRepository.findByName(RoleConstants.ADMIN.toString())
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setName(RoleConstants.ADMIN.toString());
                            return roleRepository.save(newRole);
                        });
                roleSet.add(userRole);

                User user = new User();
                user.setEmail("admin@example.com");
                user.setRoleSet(roleSet);
                user.setPassword(passwordEncoder.encode("admin"));

                userRepository.save(user);

                log.warn("An Admin has been created with email: \"admin@example.com\" and default password: \"admin\"");
            }

            if (userRepository.findByEmail("seller@example.com").isEmpty()) {
                Set<Role> roleSet = new HashSet<>();

                Role userRole = roleRepository.findByName(RoleConstants.SELLER.toString())
                        .orElseGet(() -> {
                            Role newRole = new Role();
                            newRole.setName(RoleConstants.SELLER.toString());
                            return roleRepository.save(newRole);
                        });

                roleSet.add(userRole);

                User user = new User();
                user.setEmail("seller@example.com");
                user.setRoleSet(roleSet);
                user.setPassword(passwordEncoder.encode("seller"));

                userRepository.save(user);

                log.warn("An Admin has been created with email: \"seller@example.com\" and default password: \"seller\"");
            }
        };
    }
}
