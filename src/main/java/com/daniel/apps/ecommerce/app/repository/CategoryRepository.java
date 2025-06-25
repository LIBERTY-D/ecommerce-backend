package com.daniel.apps.ecommerce.app.repository;

import com.daniel.apps.ecommerce.app.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
