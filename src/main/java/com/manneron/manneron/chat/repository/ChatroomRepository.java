package com.manneron.manneron.chat.repository;

import com.manneron.manneron.chat.entity.Chatroom;
import com.manneron.manneron.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    List<Chatroom> findAllByUser(User user);

}
