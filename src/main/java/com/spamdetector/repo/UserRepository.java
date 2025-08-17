package com.spamdetector.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spamdetector.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.contacts WHERE u.phoneNumber = :phoneNumber")
    Optional<User> findByPhoneNumberWithContacts(@Param("phoneNumber") String phoneNumber);

}


