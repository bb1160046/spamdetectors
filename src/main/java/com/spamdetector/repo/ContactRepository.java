package com.spamdetector.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spamdetector.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c.name, c.phoneNumber FROM Contact c WHERE LOWER(c.name) LIKE CONCAT(:query, '%')")
    List<Object[]> findByNameStartingWith(String query);

    @Query("SELECT c.name, c.phoneNumber FROM Contact c WHERE LOWER(c.name) LIKE %:query% AND LOWER(c.name) NOT LIKE CONCAT(:query, '%')")
    List<Object[]> findByNameContainingNotStarting(String query);

    @Query("SELECT c.name, c.phoneNumber FROM Contact c WHERE c.phoneNumber = :number")
    List<Object[]> findByPhoneNumber(String number);

    @Query("SELECT c.name FROM Contact c WHERE c.phoneNumber = :number")
    Optional<String> findFirstByPhoneNumber(String number);

    long countByPhoneNumber(String phoneNumber);
}
