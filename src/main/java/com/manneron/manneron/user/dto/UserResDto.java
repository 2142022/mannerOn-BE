package com.manneron.manneron.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserResDto {
    @Schema(description = "회원 고유 번호")
    private final Long id;
    @Schema(description = "회원 이메일")
    private final String email;
    @Schema(description = "회원 닉네임")
    private final String nickname;
    @Schema(description = "회원 성별")
    private final String gender;
    @Schema(description = "회원 하는 일")
    private final String job;
    @Schema(description = "회원 연령대")
    private final String ageRange;

    public UserResDto(Long id, String email, String nickname, String gender, String job, String ageRange) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.job = job;
        this.ageRange = ageRange;
    }
}
