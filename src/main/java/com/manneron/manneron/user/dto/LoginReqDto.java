package com.manneron.manneron.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginReqDto {
    @Schema(description = "이메일")
    private String email;
    @Schema(description = "비밀번호")
    private String password;
}
