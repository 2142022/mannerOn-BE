package com.manneron.manneron.chat.service;

import com.manneron.manneron.chat.dto.QuestionReqDto;
import com.manneron.manneron.chat.entity.Chatroom;
import com.manneron.manneron.chat.repository.ChatroomRepository;
import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatroomService {
    private final ChatroomRepository chatroomRepository;

    // 채팅방 생성
    @Transactional
    public Chatroom createChatroom(User user, QuestionReqDto questionReqDto){
        Chatroom chatroom = new Chatroom(user, questionReqDto.getContent(), questionReqDto.getCategory());
        return chatroomRepository.save(chatroom);
    }
}
