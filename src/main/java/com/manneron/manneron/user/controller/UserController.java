package com.manneron.manneron.user.controller;

import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.dto.LoginReqDto;
import com.manneron.manneron.user.dto.SignupReqDto;
import com.manneron.manneron.user.dto.UserResDto;
import com.manneron.manneron.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResDto<UserResDto> signup(@Valid @RequestBody SignupReqDto signupReqDto, HttpServletResponse httpServletResponse){
        return userService.signup(signupReqDto,httpServletResponse);
    }

    //로그인
    @PostMapping("/login")
    public ResDto<UserResDto> login(@RequestBody LoginReqDto loginReqDto, HttpServletResponse httpServletResponse){
        return userService.login(loginReqDto, httpServletResponse);
    }

    //회원조회
    @GetMapping("/{id}")
    public ResDto<UserResDto> getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

}
