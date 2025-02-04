package com.example.demospringboot.repository;

import com.example.demospringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User , Integer> {
    List<User> findAllByDeletedFalse();
}