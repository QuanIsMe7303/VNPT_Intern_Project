package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);

    @Query("SELECT r FROM Role r " +
            "JOIN r.userSet u " +
            "WHERE u.uuidUser = :uuidUser")
    Set<Role> findRolesByUserUuid(@Param("uuidUser") String uuidUser);

    @Query("SELECT r FROM Role r " +
            "JOIN r.userSet u " +
            "WHERE u.email = :email")
    Set<Role> findRolesByEmail(@Param("email") String email);
}