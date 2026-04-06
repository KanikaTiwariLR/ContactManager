package com.example.contactmanager.repository;

import com.example.contactmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}