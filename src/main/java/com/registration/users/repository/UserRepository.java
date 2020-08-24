package com.registration.users.repository;

import java.util.Optional;

import com.registration.users.model.UserSystem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserSystem, Integer>{
    
    Optional<UserSystem> findByLogin (String login);

}