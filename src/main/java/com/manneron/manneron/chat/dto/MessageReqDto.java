package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageReqDto {
    @Schema(description = "내용")
    private String content;

    @Schema(description = "역할")
    private String role;

    public MessageReqDto(String content, String role) {
        this.content = content;
        this.role = role;
    }
}
