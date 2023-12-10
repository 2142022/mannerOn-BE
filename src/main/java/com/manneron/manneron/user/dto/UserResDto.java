package com.manneron.manneron.user.dto;

import lombok.Getter;

@Getter
public class UserResDto {
    private final Long id;
    private final String password;
    private final String nickname;
    private final String gender;
    private final String job;
    private final String ageRange;

    public UserResDto(Long id, String password, String nickname, String gender, String job, String ageRange) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.job = job;
        this.ageRange = ageRange;
    }
}
