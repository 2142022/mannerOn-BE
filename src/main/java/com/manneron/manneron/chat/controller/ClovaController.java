package com.manneron.manneron.chat.controller;

import com.manneron.manneron.chat.dto.ClovaReqDto;
import com.manneron.manneron.chat.service.ClovaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class ClovaController {

    private final ClovaService clovaService;

    @PostMapping("/send-post")
    public void sendRequest(@RequestBody ClovaReqDto clovaReqDto){
        clovaService.sendHttpRequest(clovaReqDto);
    }
}
