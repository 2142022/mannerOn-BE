package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatResDto {
    @Schema(description = "채팅 내용")
    private final String chat;

    @Schema(description = "user / assistant")
    private final String role;

    @Schema(description = "좋아요 / 싫어요")
    private final int feedback;

    public ChatResDto(String chat, String role, int feedback) {
        this.chat = chat;
        this.role = role;
        this.feedback = feedback;
    }
}
