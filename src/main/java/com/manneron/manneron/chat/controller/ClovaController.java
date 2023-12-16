package com.manneron.manneron.chat.controller;

import com.manneron.manneron.chat.dto.ClovaReqDto;
import com.manneron.manneron.chat.service.ClovaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/message")
public class ClovaController {

    private final ClovaService clovaService;

    @PostMapping("/send-post")
    public void sendRequest(@RequestBody ClovaReqDto clovaReqDto) throws IOException {
        clovaService.getClovaReply(clovaReqDto);
    }
}
