package com.daniel.apps.ecommerce.app.repository;

import com.daniel.apps.ecommerce.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category")
    Collection<Product> findAllWithCategory();

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.id = " + ":id")
    Optional<Product> findByIdWithCategory(@Param("id") Long id);

}
