package com.pointers.authify.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pointers.authify.entity.UserEntity;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
