package com.manneron.manneron.chat.service;

import com.manneron.manneron.chat.dto.AnswerResDto;
import com.manneron.manneron.chat.dto.QuestionReqDto;
import com.manneron.manneron.chat.entity.Chat;
import com.manneron.manneron.chat.entity.Chatroom;
import com.manneron.manneron.chat.entity.Prompt;
import com.manneron.manneron.chat.repository.ChatRepository;
import com.manneron.manneron.chat.repository.PromptRepository;
import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    @Value("${clova.api.url}")
    private String clovaURL;
    @Value("${clova.api.key}")
    private String apiKey;
    @Value("${clova.api.gw.key}")
    private String apiGWKey;
    @Value("${clova.content.type}")
    private String contentType;

    private final ChatRepository chatRepository;
    private final ChatroomService chatroomService;
    private final PromptRepository promptRepository;

    // 채팅방 생성
    @Transactional
    public ResDto<AnswerResDto> startChat(QuestionReqDto questionReqDto, User user){
        // 채팅방 생성
        Chatroom chatroom = chatroomService.createChatroom(user, questionReqDto);

        // TODO: 시스템 지시문 저장

        // 프롬프트 내용 저장
        saveChat(chatroom, questionReqDto.getContent(), "user");

        // 프롬프트의 치환 메시지 조회
        String substitution = "";
        if (promptRepository.existsByOrigin(questionReqDto.getContent())) {
            substitution = promptRepository.findByOrigin(questionReqDto.getContent()).get().getSubstitution();
        };

        // 답변 요청
        String content = getAnswer(chatroom, substitution);

        AnswerResDto answerResDto = new AnswerResDto(chatroom.getId(), content);
        return ResDto.setSuccess(HttpStatus.OK, "답변 생성 성공", answerResDto);
    }

    // 채팅 저장
    @Transactional
    public void saveChat(Chatroom chatroom, String content, String role){
        Chat chat = new Chat(chatroom, content, role);
        chatRepository.save(chat);
    }

    // 답변 요청
    @Transactional
    public String getAnswer(Chatroom chatroom, String question){
        // 지금까지의 채팅 목록 조회
        List<Chat> chats = chatRepository.findAll();

        // Clova Studio에 답변 요청
        String answer = "";

        // 답변 저장
        saveChat(chatroom, answer, "assistant");

        return answer;
    }
}
