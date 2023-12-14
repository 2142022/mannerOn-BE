package com.manneron.manneron.chat.repository;

import com.manneron.manneron.chat.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    Boolean existsByOrigin(String origin);
    Optional<Prompt> findByOrigin(String origin);
}
