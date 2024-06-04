package com.example.library.infrastructure.repository;

import com.example.library.infrastructure.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity getUserByUsername(String username);
    Optional<UserEntity> findUserEntityByUsername(String username);
}
