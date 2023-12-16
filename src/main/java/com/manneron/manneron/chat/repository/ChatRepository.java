package com.manneron.manneron.chat.repository;

import com.manneron.manneron.chat.dto.MessageDto;
import com.manneron.manneron.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByChatroomId(Long chatroomId);

    @Query(value = "SELECT c.content, c.role FROM chats c WHERE c.chatroom_id = :chatroomId", nativeQuery = true)
    List<MessageDto> findMessageDto(@Param("chatroomId") Long chatroomId);
}
