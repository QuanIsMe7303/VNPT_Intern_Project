package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.Permission;
import com.backend.VNPT_Intern_Project.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findByName(String name);

    @Query("SELECT p FROM Permission p " +
            "JOIN p.roleSet r " +
            "WHERE r.name = :name")
    Set<Permission> findPermissionsByRoleName(@Param("name") String roleName);
}