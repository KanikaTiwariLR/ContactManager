package com.example.contactmanager.repository;

import com.example.contactmanager.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUserId(Long userId);

    // Efficiently find contacts by user ID and name keyword
    List<Contact> findByUserIdAndNameContainingIgnoreCase(Long userId, String name);

    boolean existsByUserIdAndNameIgnoreCase(Long userId, String name);
}