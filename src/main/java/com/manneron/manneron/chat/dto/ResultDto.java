package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultDto {
    @Schema(description = "답변 내용 및 역할")
    private MessageDto message;

    @Schema(description = "토큰 생성 중단 이유 (LENGTH: 길이 제한, END_TOKEN: EOD(End Of Token)로 인한 생성 중단, STOP_BEFORE: stopBefore에 지정한 문자로 인한 중단)")
    private String stopReason;

    @Schema(description = "입력 토큰 수")
    private int inputLength;

    @Schema(description = "응답 토큰 수")
    private int outputLength;

    @Schema(description = "AI Filter 점수")
    private List<AIFilterDto> aiFilter;

    public ResultDto(MessageDto message, String stopReason, int inputLength, int outputLength, List<AIFilterDto> aiFilter) {
        this.message = message;
        this.stopReason = stopReason;
        this.inputLength = inputLength;
        this.outputLength = outputLength;
        this.aiFilter = aiFilter;
    }
}
