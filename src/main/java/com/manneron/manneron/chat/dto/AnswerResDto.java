package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class AnswerResDto {
    @Schema(description = "채팅방 고유 번호")
    private final Long chatroomId;

    @Schema(description = "채팅 답변")
    private final String content;

    @Schema(description = "채팅 답변 고유 번호")
    private final Long chatId;

    public AnswerResDto(Long chatroomId, String content, Long chatId) {
        this.chatroomId = chatroomId;
        this.content = content;
        this.chatId = chatId;
    }
}
