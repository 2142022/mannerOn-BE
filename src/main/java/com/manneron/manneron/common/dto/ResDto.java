package com.manneron.manneron.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@AllArgsConstructor(staticName = "set")
@Getter
public class ResDto<T> implements Serializable {
    private HttpStatus statusCode;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient T data;

    public static <T> ResDto<T> setSuccess(HttpStatus statusCode, String message, T data) {
        return ResDto.set(statusCode, message, data);
    }

    public static <T> ResDto<T> setSuccess(HttpStatus statusCode, String message) {
        return ResDto.set(statusCode, message, null);
    }

}
