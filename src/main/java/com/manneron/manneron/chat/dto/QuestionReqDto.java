package com.manneron.manneron.chat.dto;

import com.manneron.manneron.chat.entity.Chatroom;
import com.manneron.manneron.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionReqDto {
    @Schema(description = "채팅 내용")
    private String content;

    @Schema(description = "카테고리")
    private String category;

}
