package com.daniel.apps.ecommerce.app.repository;

import com.daniel.apps.ecommerce.app.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
