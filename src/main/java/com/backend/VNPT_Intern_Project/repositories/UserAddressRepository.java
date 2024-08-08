
package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.User;
import com.backend.VNPT_Intern_Project.entities.UserAddress;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, String> {
    List<UserAddress> findByUserUuidUser(String uuidUser);
}
