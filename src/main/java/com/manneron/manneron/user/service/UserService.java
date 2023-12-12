package com.manneron.manneron.user.service;

import com.manneron.manneron.common.dto.ResDto;
import com.manneron.manneron.user.dto.LoginReqDto;
import com.manneron.manneron.user.dto.SignupReqDto;
import com.manneron.manneron.user.dto.UserResDto;
import com.manneron.manneron.user.entity.User;
import com.manneron.manneron.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{6,}$";
    private static final String NICKNAME_PATTERN = "^[a-zA-Z가-힣0-9]{1,10}$";

    @Transactional
    public ResDto<UserResDto> signup(SignupReqDto signupReqDto) {
        validateEmail(signupReqDto.getEmail());
        validatePassword(signupReqDto.getPassword());
        validateNickname(signupReqDto.getNickname());

        Optional<User> findByNicknameByEmail = userRepository.findByNickname(signupReqDto.getNickname());
        if (findByNicknameByEmail.isPresent()){
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        if (!signupReqDto.getPassword().equals(signupReqDto.getCheckPassword())){
           throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User user = new User(signupReqDto);
        userRepository.save(user);
        return ResDto.setSuccess(HttpStatus.OK,"회원가입성공");

    }

    @Transactional
    public ResDto<UserResDto> login(LoginReqDto loginReqDto) {
        validateEmail(loginReqDto.getEmail());

        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!password.equals(user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return ResDto.setSuccess(HttpStatus.OK,"로그인 성공");
    }

    @Transactional(readOnly = true)
    public ResDto<UserResDto> getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        UserResDto userResDto = new UserResDto(user.getId(), user.getEmail(), user.getNickname(), user.getJob(), user.getGender(), user.getAgeRange());

        return ResDto.setSuccess(HttpStatus.OK,"회원 조회 성공", userResDto);
    }

    //이메일 패턴 검사
    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("이메일 주소를 확인 하세요.");
        }
    }

    //비밀번호 패턴 검사
    private void validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호 조건에 맞지 않습니다.");
        }
    }

    //닉네임 패턴 검사
    private void validateNickname(String nickname) {
        Pattern pattern = Pattern.compile(NICKNAME_PATTERN);
        Matcher matcher = pattern.matcher(nickname);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("사용할 수 없는 닉네임입니다.");
        }
    }

}
