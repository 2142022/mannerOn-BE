package com.manneron.manneron.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupReqDto {

    @Email
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message = "성별은 필수 입력 값입니다.")
    private String gender;

    @NotBlank(message = "직군은 필수 입력 값입니다.")
    private String job;

    @NotBlank(message = "연령대는 필수 입력 값입니다.")
    private String ageRange;

    public SignupReqDto(String email, String password, String nickname, String gender, String job, String ageRange) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.job = job;
        this.ageRange = ageRange;
    }
}
