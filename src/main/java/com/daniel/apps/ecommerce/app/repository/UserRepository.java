package com.daniel.apps.ecommerce.app.repository;

import com.daniel.apps.ecommerce.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address")
    Collection<User> findUsersWithAddress();

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address WHERE  u.id = :id ")
    Optional<User> findUserWithAddressId(@Param("id") Long id);

    Optional<User> findUserByCreateAccountToken(String token);
}
