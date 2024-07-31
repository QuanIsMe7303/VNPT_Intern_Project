package com.backend.VNPT_Intern_Project.repositories;

import com.backend.VNPT_Intern_Project.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem, String> {
    List<CartItem> findByUuidCart(String uuidCart);

    @Query("SELECT COUNT(ci) > 0 FROM CartItem ci WHERE ci.uuidCart = :uuidCart AND ci.uuidProduct = :uuidProduct")
    boolean existsByCartAndProduct(@Param("uuidCart") String uuidCart, @Param("uuidProduct") String uuidProduct);
}
