package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatroomResDto {
    @Schema(description = "채팅방 고유 번호")
    private final Long chatroomId;

    @Schema(description = "채팅방 제목")
    private final String title;

    @Schema(description = "채팅방 카테고리")
    private final String category;

    public ChatroomResDto(Long chatroomId, String title, String category) {
        this.chatroomId = chatroomId;
        this.title = title;
        this.category = category;
    }
}
