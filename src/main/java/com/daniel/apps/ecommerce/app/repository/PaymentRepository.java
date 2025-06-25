package com.daniel.apps.ecommerce.app.repository;

import com.daniel.apps.ecommerce.app.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
