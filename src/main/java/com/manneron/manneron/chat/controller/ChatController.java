package com.manneron.manneron.chat.controller;

import com.manneron.manneron.chat.dto.AnswerResDto;
import com.manneron.manneron.chat.dto.ChatResDto;
import com.manneron.manneron.chat.dto.ChatroomResDto;
import com.manneron.manneron.chat.dto.QuestionReqDto;
import com.manneron.manneron.chat.service.ChatService;
import com.manneron.manneron.chat.service.ChatroomService;
import com.manneron.manneron.chat.service.PromptService;
import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.dto.UserResDto;
import com.manneron.manneron.user.entity.User;
import com.manneron.manneron.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
@Tag(name = "Chat", description = "채팅 관리 API")
public class ChatController {
    private final UserService userService;
    private final ChatroomService chatroomService;
    private final ChatService chatService;
    private final PromptService promptService;

//    @PostMapping("/prompt")
//    @Operation(summary = "프롬프트로 채팅방 생성")
//    public ResDto<AnswerResDto> createChatroomByPrompt(@RequestBody QuestionReqDto questionReqDto){
//        // 회원 조회
//        // 채팅방 생성
//        // 프롬프트의 치환 메시지 확인
//        // 답변 요청
//        return ;
//    }
//
//    @PostMapping("/type")
//    @Operation(summary = "직접 메시지를 입력하여 채팅방 생성")
//    public ResDto<AnswerResDto> createChatroomByTyping(@RequestBody QuestionReqDto questionReqDto){
//        // 회원 조회
//        // 채팅방 생성
//        // 답변 요청
//        return ;
//    }
//
//    @PostMapping("/{chatroom_id}")
//    @Operation(summary = "기존 채팅방에서 답변 요청")
//    public ResDto<AnswerResDto> getAnswer(@PathVariable Long chatroomId, @RequestBody QuestionReqDto questionReqDto) {
//        // 회원 조회
//        // 이전 채팅 메시지 내용 조회
//        // 답변 요청
//        return ;
//    }
//
//    @GetMapping("/")
//    @Operation(summary = "채팅 목록 조회")
//    public ResDto<ChatroomResDto> getChatroomList(){
//        // 회원 조회
//        // 채팅방 목록 조회
//        return ;
//    }
//
//    @GetMapping("/{chatroom_id}")
//    @Operation(summary = "이전 채팅 내용 조회")
//    public ResDto<ChatResDto> getChatList(@PathVariable Long chatroomId){
//        // 회원 조회
//        // 이전 채팅 내용 조회
//        return ;
//    }
}
