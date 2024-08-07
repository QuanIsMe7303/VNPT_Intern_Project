
package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = "roleSet")
    Optional<User> findByEmail(String email);
}
