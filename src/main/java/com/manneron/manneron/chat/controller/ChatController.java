package com.manneron.manneron.chat.controller;

import com.manneron.manneron.chat.dto.*;
import com.manneron.manneron.chat.service.ChatService;
import com.manneron.manneron.chat.service.ChatroomService;
import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.common.security.UserDetailsImpl;
import com.manneron.manneron.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "Chat", description = "채팅 관리 API")
public class ChatController {
    private final ChatroomService chatroomService;
    private final ChatService chatService;

    private final UserRepository userRepository;

    @PostMapping("/start")
    @Operation(summary = "채팅방 생성 & 첫 답변 요청")
//    public ResDto<AnswerResDto> startChat(@RequestBody QuestionReqDto questionReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
    public ResDto<AnswerResDto> startChat(@RequestBody QuestionReqDto questionReqDto) throws IOException {
//        return chatService.startChat(questionReqDto, userDetails.user());
        return chatService.startChat(questionReqDto, userRepository.findById(6L).get());
    }

    @PostMapping("/{chatroomId}")
    @Operation(summary = "기존 채팅방에서 답변 요청")
//    public ResDto<AnswerResDto> getAnswer(@PathVariable Long chatroomId, @RequestBody QuestionReqDto questionReqDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
    public ResDto<AnswerResDto> getAnswer(@PathVariable Long chatroomId, @RequestBody QuestionReqDto questionReqDto) throws IOException {
        return chatService.getAnswer(chatroomId, questionReqDto, userRepository.findById(6L).get());
    }

    @GetMapping("/")
    @Operation(summary = "채팅방 목록 조회")
//    public ResDto<List<ChatroomResDto>> getChatroomList(@AuthenticationPrincipal UserDetailsImpl userDetails){
    public ResDto<List<ChatroomResDto>> getChatroomList(){
        return chatroomService.getChatroomList(userRepository.findById(6L).get());
    }

    @GetMapping("/{chatroomId}")
    @Operation(summary = "이전 채팅 내용 조회")
//    public ResDto<List<ChatResDto>> getChatList(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long chatroomId){
    public ResDto<List<ChatResDto>> getChatList(@PathVariable Long chatroomId){
        return chatService.getChatList(chatroomId);
    }

    @PutMapping("/feedback/{chatId}")
    @Operation(summary = "좋아요 / 싫어요 갱신")
//    public ResDto<Boolean> updateFeedback(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long chatId, @RequestBody FeedbackReqDto feedbackReqDto){
    public ResDto<Boolean> updateFeedback(@PathVariable Long chatId, @RequestBody FeedbackReqDto feedbackReqDto){
        return chatService.updateFeedback(chatId, feedbackReqDto.getFeedback());
    }

    @PutMapping("/copy/{chatId}")
    @Operation(summary = "복사 횟수 증가")
//    public ResDto<Boolean> updateCopy(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long chatId){
    public ResDto<Boolean> updateCopy(@PathVariable Long chatId){
        return chatService.updateCopy(chatId);
    }

}
