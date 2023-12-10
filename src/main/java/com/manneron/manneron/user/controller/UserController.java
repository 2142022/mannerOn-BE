package com.manneron.manneron.user.controller;

import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.dto.LoginReqDto;
import com.manneron.manneron.user.dto.SignupReqDto;
import com.manneron.manneron.user.dto.UserResDto;
import com.manneron.manneron.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResDto<UserResDto> signup(@Valid @RequestBody SignupReqDto signupReqDto){
        return userService.signup(signupReqDto);
    }

    @PostMapping("/login")
    public ResDto<UserResDto> login(@RequestBody LoginReqDto loginReqDto){
        return userService.login(loginReqDto);
    }
}
