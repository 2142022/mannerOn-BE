package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClovaResDto {
    @Schema(description = "응답 상태 코드 및 메시지")
    private StatusDto status;

    @Schema(description = "응답 데이터")
    private ResultDto result;

    public ClovaResDto(StatusDto status, ResultDto result) {
        this.status = status;
        this.result = result;
    }
}
