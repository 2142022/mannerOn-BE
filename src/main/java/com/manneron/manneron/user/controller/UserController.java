package com.manneron.manneron.user.controller;

import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.dto.LoginReqDto;
import com.manneron.manneron.user.dto.SignupReqDto;
import com.manneron.manneron.user.dto.UserResDto;
import com.manneron.manneron.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관리 API")
public class UserController {

    private final UserService userService;

    //이메일 중복 검사
    @GetMapping("/check-email")
    @Operation(summary = "이메일 중복 검사")
    public ResDto<Boolean> checkEmail(@RequestParam String email){
        return userService.checkEmail(email);
    }

    //회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원 가입")
    public ResDto<UserResDto> signup(@Valid @RequestBody SignupReqDto signupReqDto, HttpServletResponse httpServletResponse){
        return userService.signup(signupReqDto,httpServletResponse);
    }

    //로그인
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResDto<UserResDto> login(@RequestBody LoginReqDto loginReqDto, HttpServletResponse httpServletResponse){
        return userService.login(loginReqDto, httpServletResponse);
    }

    //회원조회
    @GetMapping("/{id}")
    @Operation(summary = "회원 조회")
    public ResDto<UserResDto> getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

}
