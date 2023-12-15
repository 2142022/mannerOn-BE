package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatResDto {
    @Schema(description = "채팅 고유 번호")
    private final Long chatId;
    @Schema(description = "채팅 내용")
    private final String chat;

    @Schema(description = "user / assistant")
    private final String role;

    @Schema(description = "좋아요 / 싫어요")
    private final int feedback;

    public ChatResDto(Long chatId, String chat, String role, int feedback) {
        this.chatId = chatId;
        this.chat = chat;
        this.role = role;
        this.feedback = feedback;
    }
}
