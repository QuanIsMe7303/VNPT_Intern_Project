package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.CartItem;
import com.backend.VNPT_Intern_Project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, String> {
//    List<CartItem> findByUuidCart(String uuidCart);

    List<CartItem> findByUuidUser(String uuidUser);

    @Query("SELECT COUNT(ci) > 0 FROM CartItem ci WHERE ci.uuidUser = :uuidUser AND ci.uuidProduct = :uuidProduct")
    boolean existsByCartAndProduct(@Param("uuidUser") String uuidCart, @Param("uuidProduct") String uuidProduct);
}
