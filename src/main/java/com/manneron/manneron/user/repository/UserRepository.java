package com.manneron.manneron.user.repository;

import com.manneron.manneron.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickName);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
