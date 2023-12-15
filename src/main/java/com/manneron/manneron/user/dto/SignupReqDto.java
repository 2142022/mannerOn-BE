package com.manneron.manneron.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupReqDto {

    @Email
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Schema(description = "이메일")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Schema(description = "비밀번호")
    private String password;

    @NotBlank(message = "비밀번호를 확인 해 주세요.")
    @Schema(description = "비밀번호 확인")
    private String checkPassword;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "성별")
    private String gender;

    @Schema(description = "하는 일")
    private String job;

    @Schema(description = "연령대")
    private String ageRange;

}
