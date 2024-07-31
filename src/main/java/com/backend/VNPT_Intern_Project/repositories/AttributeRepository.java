
package com.backend.vnptproject.repositories;

import com.backend.vnptproject.entities.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, String> {
//    List<Attribute> findByUuidProduct(String uuidProduct);

    Attribute findByAttributeKey(String attributeKey);
}
