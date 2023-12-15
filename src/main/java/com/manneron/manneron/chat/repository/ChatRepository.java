package com.manneron.manneron.chat.repository;

import com.manneron.manneron.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllByChatroomId(Long chatroomid);
}
