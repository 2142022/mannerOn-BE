package com.manneron.manneron.chat.controller;

import com.manneron.manneron.chat.dto.*;
import com.manneron.manneron.chat.service.ChatService;
import com.manneron.manneron.chat.service.ChatroomService;
import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.common.security.UserDetailsImpl;
import com.manneron.manneron.user.repository.UserRepository;
import com.manneron.manneron.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "Chat", description = "채팅 관리 API")
public class ChatController {
    private final ChatroomService chatroomService;
    private final ChatService chatService;

    @PostMapping("/start")
    @Operation(summary = "채팅방 생성 & 첫 답변 요청")
    public ResDto<AnswerResDto> startChat(@RequestBody QuestionReqDto questionReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return chatService.startChat(questionReqDto, userDetails.user());
    }

    @PostMapping("/{chatroomId}")
    @Operation(summary = "기존 채팅방에서 답변 요청")
    public ResDto<AnswerResDto> getAnswer(@PathVariable Long chatroomId, @RequestBody QuestionReqDto questionReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return chatService.getAnswer(chatroomId, questionReqDto, userDetails.user());
    }

    @GetMapping("/")
    @Operation(summary = "채팅방 목록 조회")
    public ResDto<List<ChatroomResDto>> getChatroomList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return chatroomService.getChatroomList(userDetails.user());
    }

    @GetMapping("/{chatroomId}")
    @Operation(summary = "이전 채팅 내용 조회")
    public ResDto<List<ChatResDto>> getChatList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long chatroomId){
        return chatService.getChatList(userDetails.user().getId());
    }

    @PutMapping("/feedback/{chatId}")
    @Operation(summary = "좋아요 / 싫어요 갱신")
    public ResDto<Boolean> updateFeedback(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long chatId, @RequestBody FeedbackReqDto feedbackReqDto){
        return chatService.updateFeedback(chatId, feedbackReqDto.getFeedback());
    }

    @PutMapping("/copy/{chatId}")
    @Operation(summary = "복사 횟수 증가")
    public ResDto<Boolean> updateCopy(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long chatId){
        return chatService.updateCopy(chatId);
    }

}
