package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ClovaReqDto {
    @Schema(description = "메시지")
    private List<MessageDto> messages;

    @Schema(description = "설정값이 높을 수록 다양한 문장 생성 (0 < temperature <= 1)")
    private Double temperature;

    @Schema(description = "생성 토큰 후보군에서 확률이 높은 k개를 후보로 지정하여 샘플링 (0 ≦ topK ≦ 128)")
    private int topK;

    public ClovaReqDto(List<MessageDto> messages, Double temperature, int topK) {
        this.messages = messages;
        this.temperature = temperature;
        this.topK = topK;
    }
}
