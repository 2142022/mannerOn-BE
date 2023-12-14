package com.manneron.manneron.chat.dto;

import com.manneron.manneron.chat.entity.Chat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ClovaReqDto {
    @Schema(description = "메시지")
    private List<Chat> messages;


}
