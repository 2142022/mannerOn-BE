package com.manneron.manneron.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manneron.manneron.chat.dto.*;
import com.manneron.manneron.chat.entity.Chat;
import com.manneron.manneron.chat.entity.Chatroom;
import com.manneron.manneron.chat.repository.ChatRepository;
import com.manneron.manneron.chat.repository.ChatroomRepository;
import com.manneron.manneron.chat.repository.PromptRepository;
import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.common.exception.GlobalException;
import com.manneron.manneron.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.manneron.manneron.common.exception.ExceptionEnum.*;

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
    private final ChatroomRepository chatroomRepository;

    // TODO: 시스템 지시문
    private final static String systemMessage = "";

    // 채팅방 생성 & 첫 답변 요청
    @Transactional
    public ResDto<AnswerResDto> startChat(QuestionReqDto questionReqDto, User user) throws IOException {
        // 채팅방 생성
        Chatroom chatroom = chatroomService.createChatroom(user, questionReqDto);

        // 시스템 지시문 저장
        saveChat(chatroom, systemMessage, "system");

        // 프롬프트 내용 저장
        saveChat(chatroom, questionReqDto.getContent(), "user");

        // 프롬프트의 치환 메시지 조회
        String substitution = "";
        if (promptRepository.existsByOrigin(questionReqDto.getContent())) {
            substitution = promptRepository.findByOrigin(questionReqDto.getContent()).get().getSubstitution();
        }

        // Clova Studion에 답변 요청
        String content = getClovaReply(chatroom, substitution);

        AnswerResDto answerResDto = new AnswerResDto(chatroom.getId(), content);
        return ResDto.setSuccess(HttpStatus.OK, "답변 생성 성공", answerResDto);
    }

    // 채팅 저장
    @Transactional
    public void saveChat(Chatroom chatroom, String content, String role){
        Chat chat = new Chat(chatroom, content, role);
        chatRepository.save(chat);
    }

    // Clova Studion에 답변 요청
    @Transactional
    public String getClovaReply(Chatroom chatroom, String question) throws IOException {
        // 지금까지의 채팅 목록 조회
        List<Chat> chatList = chatRepository.findAllByChatroomId(chatroom.getId());
        List<MessageReqDto> messageReqDtoList = new ArrayList<>();
        for (Chat chat : chatList){
            messageReqDtoList.add(new MessageReqDto(chat.getContent(), chat.getRole()));
        }

        // Clova Studio에 연결
        String apiURL = clovaURL + "testapp/v1/chat-completions/HCX-002";
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", apiGWKey);
        con.setRequestProperty("Content-Type", contentType);

        // 데이터 전송
        con.setDoOutput(true);
        ClovaReqDto clovaReqDto = new ClovaReqDto(messageReqDtoList, 0.8, 90, 0.8);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReq = objectMapper.writeValueAsString(clovaReqDto);
        try (OutputStream os = con.getOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
            osw.write(jsonReq);
            osw.flush();
        }

        // 응답 코드 확인
        int responseCode = con.getResponseCode();
        System.out.println("HTTP 응답 코드: " + responseCode);

        // 응답 메시지 확인
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("응답 바디: " + response.toString());
            }
        } else {
            System.out.println("에러 응답: " + responseCode);
        }

        con.disconnect();

        // 답변 저장
        String answer = "";
        saveChat(chatroom, answer, "assistant");

        return answer;
    }

    // 채팅방에서 답변 요청
    @Transactional
    public ResDto<AnswerResDto> getAnswer(Long chatroomId, QuestionReqDto questionReqDto, User user) throws IOException {
        // 채팅방 조회
        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow(
                ()-> new GlobalException(NOT_FOUND_CHATROOM));

        // 사용자가 질문한 내용 저장
        saveChat(chatroom, questionReqDto.getContent(), "user");

        // Clova Studion에 답변 요청
        String content = getClovaReply(chatroom, questionReqDto.getContent());

        AnswerResDto answerResDto = new AnswerResDto(chatroom.getId(), content);
        return ResDto.setSuccess(HttpStatus.OK, "답변 생성 성공", answerResDto);
    }

    // 이전 채팅 목록 조회 (시스템 지시문 제외)
    @Transactional
    public ResDto<List<ChatResDto>> getChatList(Long chatroomId) {
        List<Chat> chatList = chatRepository.findAllByChatroomId(chatroomId);

        List<ChatResDto> chatResDtoList = new ArrayList<>();
        for (int i=1; i < chatList.size(); i++){
            Chat chat = chatList.get(i);
            chatResDtoList.add(new ChatResDto(chat.getId(), chat.getContent(), chat.getRole(), chat.getFeedback()));
        }

        return ResDto.setSuccess(HttpStatus.OK, "이전 채팅 조회 성공", chatResDtoList);
    }

    // 좋아요 / 싫어요 갱신
    @Transactional
    public ResDto<Boolean> updateFeedback(Long chatId, int feedback) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                () -> new GlobalException(NOT_FOUND_CHAT)
        );
        chat.setFeedback(feedback);
        chatRepository.save(chat);
        return ResDto.setSuccess(HttpStatus.OK, "피드백 수정 성공");
    }

    // 복사 횟수 증가
    @Transactional
    public ResDto<Boolean> updateCopy(Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(
                () -> new GlobalException(NOT_FOUND_CHAT)
        );
        chat.setCopy(chat.getCopy() + 1);
        chatRepository.save(chat);
        return ResDto.setSuccess(HttpStatus.OK, "복사 횟수 갱신 성공");
    }
}
