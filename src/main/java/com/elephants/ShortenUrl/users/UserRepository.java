package com.elephants.ShortenUrl.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :username")
    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    @Query(nativeQuery = true, value = "SELECT id FROM users WHERE username = :username")
    Long findIdByUsername(String username);
}