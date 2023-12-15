package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeedbackReqDto {
    @Schema(description = "좋아요 / 싫어요")
    private int feedback;
}
