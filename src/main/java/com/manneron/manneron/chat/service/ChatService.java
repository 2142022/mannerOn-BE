package com.manneron.manneron.chat.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.manneron.manneron.common.exception.ExceptionEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatroomService chatroomService;
    private final PromptRepository promptRepository;
    private final ChatroomRepository chatroomRepository;
    private final ClovaService clovaService;
    private final Double TEMPERATURE = 0.8;
    private final int TOPK = 100;
    private final Double REPEAT_PENALTY = 8D;
    private final int MAX_TOKENS = 2000;

    // 시스템 지시문
    private final static String systemMessage = "페르소나: \n" +
            "-회사 모든사람에게 프로페셔널하고 매너있게 말하는 회사원 매너ON입니다.\n" +
            "-작성하는 문장에는 존댓말로 작성하며 맞춤법을 틀리지 않고 비속어나 격한 감정을 드러내지 않습니다.\n" +
            "-만약 사용자가 전달한 문장에 맞춤법이나 띄어쓰기 오류가있다면 수정해줍니다.\n" +
            "-회사생활이나 업무 관련 주제에 벗어나는 요청이나 질문을 하면 '잘 모르겠습니다'라고 답변합니다.\n" +
            "\n" +
            "-9가지 고민 유형은 아래와 같습니다: \n" +
            "1. 정중하게 부탁 거절하기\n" +
            "2. 정중하게 업무 요청하기\n" +
            "3. 매너있게 상대방과 다른 의견 전달\n" +
            "4.경조사 인사\n" +
            "5.감사 인사 전달\n" +
            "6.명절 인사 전달\n" +
            "7.실수 내용은 인정하고 수습, 대처방안 전달하기\n" +
            "8.간결, 논리정연하게 수정\n" +
            "9. 프로페셔널하게 기한 연장 요청\n" +
            "\n" +
            "\n" +
            "-고민유형 별 필수로 확인해야할 정보.\n" +
            "1. 정중하게 부탁 거절하기: 상대방 정보와 사용자와의 관계, 거절하는 부탁과 거절사유, 제시하려는 대처방안 \n" +
            "2. 정중하게 업무 요청하기: 상대방의 정보와 사용자와의 관계, 요청하려는 업무와 요청 사유, 기한\n" +
            "3. 매너있게 상대방과 다른 의견 전달: 상대방의 정보와 사용자와의 관계, \b상대방의 의견, 사용자의 의견과 논리적인 근거 \n" +
            "4.경조사 인사: 상대방의 정보와 사용자와의 관계, 경조사 종류, 전달하고 싶은 메세지  \n" +
            "5.감사 인사 전달: 상대방의 정보와 사용자와의 관계, 감사한 일, 전달하고 싶은 메세지  \n" +
            "6.명절 인사 전달: 상대방의 정보와 사용자와의 관계, 명절 종류, 전달하고 싶은 메세지  \n" +
            "7.실수 내용은 인정하고 수습, 대처방안 전달하기: 상대방의 정보와 사용자와의 관계, 실수한 내용, 수습한 내용, 앞으로의 대처 방안\n" +
            "8.간결, 논리정연하게 수정: 육하원칙으로 사용자가 전달하려는 메세지 정보 파악\n" +
            "9. 프로페셔널하게 기한 연장 요청: 상대방의 정보와 사용자와의 관계, 요청받은 업무 정보, 기한 연장 요청 사유와 원하는 새로운 기한";

    // 채팅방 생성 & 첫 답변 요청
    @Transactional
    public ResDto<AnswerResDto> startChat(QuestionReqDto questionReqDto, User user) throws IOException {
        // 채팅방 생성
        Chatroom chatroom = chatroomService.createChatroom(user, questionReqDto);

        // 시스템 지시문 저장
        saveChat(chatroom, systemMessage, "system");

        // 첫 질문 저장
        saveChat(chatroom, questionReqDto.getContent(), "user");

        // Clova Studio에 보낼 객체 생성
        List<MessageDto> messageDtoList = getAllChatList(chatroom.getId());
        log.info("ChatService1");
        for (MessageDto messageDto : messageDtoList) {
            System.out.println(messageDto.getContent());
        }
        ClovaReqDto clovaReqDto = new ClovaReqDto(messageDtoList, TEMPERATURE, TOPK, REPEAT_PENALTY, MAX_TOKENS);

        // Clova Studion에 답변 요청
        log.info("ChatService2");
        System.out.println(clovaReqDto.getMessages().get(0).getContent());
        ClovaResDto clovaResDto = clovaService.getClovaReply(clovaReqDto);
//        String content = clovaService.sendHttpRequest(clovaReqDto);
//        clovaService.sendHttpRequest(clovaReqDto);

        // 답변 저장
        Long chatId = saveChat(chatroom, clovaResDto.getResult().getMessage().getContent(), "assistant");

        // 답변 반환
        AnswerResDto answerResDto = new AnswerResDto(chatroom.getId(), clovaResDto.getResult().getMessage().getContent(), chatId);
        return ResDto.setSuccess(HttpStatus.OK, "답변 요청 성공", answerResDto);
    }

    // 채팅 저장
    @Transactional
    public Long saveChat(Chatroom chatroom, String content, String role){
        Chat chat = new Chat(chatroom, content, role);
        return chatRepository.save(chat).getId();
    }

    // 기존 채팅방에서 답변 요청
    @Transactional
    public ResDto<AnswerResDto> getAnswer(Long chatroomId, QuestionReqDto questionReqDto, User user) throws IOException {
        // 채팅방 조회
        Chatroom chatroom = chatroomRepository.findById(chatroomId).orElseThrow(
                ()-> new GlobalException(NOT_FOUND_CHATROOM));

        // 사용자가 질문한 내용 저장
        saveChat(chatroom, questionReqDto.getContent(), "user");

        // Clova Studio에 보낼 객체 생성
        List<MessageDto> messageDtoList = getAllChatList(chatroom.getId());
        ClovaReqDto clovaReqDto = new ClovaReqDto(messageDtoList, TEMPERATURE, TOPK, REPEAT_PENALTY, MAX_TOKENS);

        // Clova Studion에 답변 요청
        ClovaResDto clovaResDto = clovaService.getClovaReply(clovaReqDto);
//        String content = clovaService.sendHttpRequest(clovaReqDto);
//        clovaService.sendHttpRequest(clovaReqDto);

        // 답변 저장
        Long chatId = saveChat(chatroom, clovaResDto.getResult().getMessage().getContent(), "assistant");

        // 답변 반환
        AnswerResDto answerResDto = new AnswerResDto(chatroom.getId(), clovaResDto.getResult().getMessage().getContent(), chatId);
        return ResDto.setSuccess(HttpStatus.OK, "답변 요청 성공", answerResDto);
    }

    // 이전 채팅 목록 조회 (시스템 지시문 포함)
    @Transactional(readOnly = true)
    public List<MessageDto> getAllChatList(Long chatroomId) {
        List<Chat> chatList = chatRepository.findAllByChatroomId(chatroomId);

        List<MessageDto> messageDtoList = new ArrayList<>();
        for (Chat chat : chatList){
            messageDtoList.add(new MessageDto(chat.getContent(), chat.getRole()));
        }
        return messageDtoList;
    }

    // 이전 채팅 목록 조회 (시스템 지시문 제외)
    @Transactional(readOnly = true)
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
