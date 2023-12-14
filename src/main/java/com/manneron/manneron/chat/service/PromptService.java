package com.manneron.manneron.chat.service;

import com.manneron.manneron.chat.dto.QuestionReqDto;
import com.manneron.manneron.chat.entity.Prompt;
import com.manneron.manneron.chat.repository.PromptRepository;
import com.manneron.manneron.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromptService {
    private final PromptRepository promptRepository;

    // 프롬프트의 치환 메시지 조회
    @Transactional(readOnly = true)
    public String getSubstitution(QuestionReqDto questionReqDto){
        return promptRepository.findByOrigin(questionReqDto.getContent()).get().getSubstitution();
    }
}
