package com.dev.armond.user.repository;

import com.dev.armond.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
}
