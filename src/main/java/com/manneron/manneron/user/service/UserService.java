package com.manneron.manneron.user.service;

import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.common.exception.GlobalException;
import com.manneron.manneron.common.jwt.JwtUtil;
import com.manneron.manneron.user.dto.LoginReqDto;
import com.manneron.manneron.user.dto.SignupReqDto;
import com.manneron.manneron.user.dto.UserResDto;
import com.manneron.manneron.user.entity.User;
import com.manneron.manneron.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.manneron.manneron.common.exception.ExceptionEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$";
    private static final String NICKNAME_PATTERN = "^[a-zA-Z가-힣0-9]{1,10}$";

    @Transactional
    public ResDto<Boolean> checkEmail(String email){
        validateEmail(email);

        boolean exists = userRepository.existsByEmail(email);
        if (exists){
            throw new GlobalException(DUPLICATED_EMAIL);
        }
        return ResDto.setSuccess(HttpStatus.OK,"사용가능한 이메일 입니다.");
    }

    @Transactional
    public ResDto<UserResDto> signup(SignupReqDto signupReqDto, HttpServletResponse httpServletResponse) {
        validateEmail(signupReqDto.getEmail());
        validatePassword(signupReqDto.getPassword());
        validateNickname(signupReqDto.getNickname());

        Optional<User> findByNicknameByEmail = userRepository.findByNickname(signupReqDto.getNickname());
        if (findByNicknameByEmail.isPresent()){
            throw new GlobalException(DUPLICATED_NICK_NAME);
        }
        if (!signupReqDto.getPassword().equals(signupReqDto.getCheckPassword())){
           throw new GlobalException(CHECK_PASSWORD);
        }

        String password = passwordEncoder.encode(signupReqDto.getPassword());

        User user = new User(password,signupReqDto);
        userRepository.save(user);

        jwtUtil.createAndSetToken(httpServletResponse, user.getEmail(), user.getId());
        return ResDto.setSuccess(HttpStatus.OK,"회원가입성공");

    }

    @Transactional
    public ResDto<UserResDto> login(LoginReqDto loginReqDto, HttpServletResponse httpServletResponse) {
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();

        validateEmail(email);

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new GlobalException(BAD_EMAIL));

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new GlobalException(BAD_PASSWORD);
        }

        jwtUtil.createAndSetToken(httpServletResponse, user.getEmail(), user.getId());
        return ResDto.setSuccess(HttpStatus.OK,"로그인 성공");
    }

    @Transactional(readOnly = true)
    public ResDto<UserResDto> getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(
                ()-> new GlobalException(NOT_FOUND_USER));

        UserResDto userResDto = new UserResDto(user.getId(), user.getEmail(), user.getNickname(), user.getJob()
                                                ,user.getGender(), user.getAgeRange());

        return ResDto.setSuccess(HttpStatus.OK,"회원 조회 성공", userResDto);
    }

    //이메일 패턴 검사
    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new GlobalException(EMAIL_REGEX);
        }
    }

    //비밀번호 패턴 검사
    private void validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new GlobalException(PASSWORD_REGEX);
        }
    }

    //닉네임 패턴 검사
    private void validateNickname(String nickname) {
        Pattern pattern = Pattern.compile(NICKNAME_PATTERN);
        Matcher matcher = pattern.matcher(nickname);
        if (!matcher.matches()) {
            throw new GlobalException(NICKNAME_REGEX);
        }
    }

}
