package com.manneron.manneron.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {
    // 400 Bad Request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "이메일 또는 비밀번호가 일치하지 않습니다."),
    BAD_EMAIL(HttpStatus.BAD_REQUEST, "400", "존재하지 않는 사용자"),
    BAD_PASSWORD(HttpStatus.BAD_REQUEST, "400", "비밀번호가 일치하지 않습니다."),
    PRESENT_PASSWORD(HttpStatus.BAD_REQUEST, "400", "입력한 비밀번호와 기존 비밀번호가 일치하지 않습니다."),
    CHECK_PASSWORD(HttpStatus.BAD_REQUEST, "400", "입력한 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    PASSWORD_REGEX(HttpStatus.BAD_REQUEST, "400", "비밀번호는 8~15자리, 영어, 숫자, 특수문자 조합으로 구성되어야 합니다."),
    NICKNAME_REGEX(HttpStatus.BAD_REQUEST, "400", "닉네임은 10자리이내로 문자, 숫자 조합으로 구성되어야합니다."),
    EMAIL_REGEX(HttpStatus.BAD_REQUEST, "400", "이메일 형식이 올바르지 않습니다."),
    COINCIDE_PASSWORD(HttpStatus.BAD_REQUEST, "400", "새로운 비밀번호와 확인 비밀번호가 일치하지 않습니다."),
    REQUEST_DATA_BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", "요청 데이터가 NULL 인지 확인 하세요."),

    // 401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", "권한이 없습니다."),
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "401", "유효하지 않은 token입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "401", "Expired JWT token, 만료된 JWT token 입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "401", "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다."),
    JWT_CLAIMS_IS_EMPTY(HttpStatus.UNAUTHORIZED, "401", "JWT claims is empty, 잘못된 JWT 토큰 입니다."),


    // 404 Not Found
    NOT_FOUND_CHAT(HttpStatus.NOT_FOUND, "404_1", "채팅이 존재하지 않습니다."),
    NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND, "404_2", "채팅방이 존재하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "404_3", "회원이 존재하지 않습니다."),

    // 409 Conflict
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "409", "중복된 아이디가 이미 존재합니다."),
    DUPLICATED_NICK_NAME(HttpStatus.CONFLICT, "409", "중복된 닉네임이 이미 존재합니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "409", "중복된 이메일이 이미 존재합니다."),
    EXISTED_NICK_NAME(HttpStatus.CONFLICT, "409", "기존 닉네임과 동일합니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
