package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusDto {
    @Schema(description = "응답 상태 코드")
    private String code;

    @Schema(description = "응답 메시지")
    private String message;

    public StatusDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
