package com.manneron.manneron.chat.service;

import com.manneron.manneron.chat.dto.ChatroomResDto;
import com.manneron.manneron.chat.dto.QuestionReqDto;
import com.manneron.manneron.chat.entity.Chatroom;
import com.manneron.manneron.chat.repository.ChatroomRepository;
import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    // 채팅방 목록 조회
    @Transactional(readOnly = true)
    public ResDto<List<ChatroomResDto>> getChatroomList(User user){
        List<Chatroom> chatroomList = chatroomRepository.findAllByUser(user);

        if (chatroomList == null){
            return ResDto.setSuccess(HttpStatus.NO_CONTENT, "채팅방이 존재하지 않습니다.", null);
        }

        List<ChatroomResDto> chatroomResDtoList = new ArrayList<>();
        for (Chatroom chatroom : chatroomList){
            chatroomResDtoList.add(new ChatroomResDto(chatroom.getId(), chatroom.getTitle(), chatroom.getCategory()));
        }

        return ResDto.setSuccess(HttpStatus.OK, "답변 생성 성공", chatroomResDtoList);
    }
}
