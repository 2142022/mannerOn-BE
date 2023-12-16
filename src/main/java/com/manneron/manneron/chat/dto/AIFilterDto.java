package com.manneron.manneron.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AIFilterDto {
    @Schema(description = "카테고리 (curse / unsafeContents)")
    private String groupName;

    @Schema(description = "카테고리 세부 이름 (discrimination: 비하, 차별, 혐오(curse), insult: 욕설(curse), sexualHarassment: 성희롱, 음란(unsafeContents))")
    private String name;

    @Schema(description = "AI Filter 점수 (0: 대화 메시지에 민감/위험 표현 포함 가능성 높음, 1: 대화 메시지에 민감/위험 표현 포함 가능성 있음, 2: 대화 메시지에 민감/위험 표현 포함 가능성 낮음)")
    private String score;

    public AIFilterDto(String groupName, String name, String score) {
        this.groupName = groupName;
        this.name = name;
        this.score = score;
    }
}
