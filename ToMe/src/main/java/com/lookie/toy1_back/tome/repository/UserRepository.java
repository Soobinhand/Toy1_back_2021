package com.lookie.toy1_back.tome.repository;

import com.lookie.toy1_back.tome.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

    Optional<User> findById(String username);
}
