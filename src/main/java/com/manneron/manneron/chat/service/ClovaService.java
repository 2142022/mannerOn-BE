package com.manneron.manneron.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manneron.manneron.chat.dto.ClovaReqDto;
import com.manneron.manneron.chat.dto.ClovaResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Slf4j
@Service
@RequiredArgsConstructor
public class ClovaService {
    @Value("${clova.api.url}")
    private String clovaURL;
    @Value("${clova.api.key}")
    private String apiKey;
    @Value("${clova.api.gw.key}")
    private String apiGWKey;
    @Value("${clova.content.type}")
    private String contentType;

//    public void sendHttpRequest(ClovaReqDto clovaReqDto) throws IOException {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
//        headers.set("X-NCP-APIGW-API-KEY", apiGWKey);
//        headers.set("Content-Type", contentType);
//
//        HttpEntity<ClovaReqDto> requestEntity = new HttpEntity<>(clovaReqDto, headers);
//
//        ResponseEntity<ClovaResDto> responseEntity = restTemplate.exchange(
//                clovaURL,
//                HttpMethod.POST,
//                requestEntity,
//                ClovaResDto.class
//        );
//
//        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
//        if(statusCode == HttpStatus.OK){
//            ClovaResDto responseBody = responseEntity.getBody();
//            System.out.println(responseBody.getResult().getMessage().getContent());
//        }else{
//            log.error("HTTP 요청이 실패했습니다. 상태 코드: {}", statusCode);
//            throw new RuntimeException("HTTP 요청이 실패했습니다.");
//        }
//    }

    // Clova Studion에 답변 요청
    public ClovaResDto getClovaReply(ClovaReqDto clovaReqDto) throws IOException {
        // Clova Studio에 연결
        String apiURL = clovaURL;
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        con.setRequestProperty("X-NCP-APIGW-API-KEY", apiGWKey);
        con.setRequestProperty("Content-Type", contentType);

        // 데이터 전송
        con.setDoOutput(true);
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(clovaReqDto);
        try (OutputStream os = con.getOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
            osw.write(data);
            osw.flush();
        }

        // 응답 코드 확인
        int responseCode = con.getResponseCode();

        // 응답 메시지 확인
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream is = con.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                 BufferedReader br = new BufferedReader(isr)) {

                StringBuilder responseStringBuilder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    responseStringBuilder.append(line);
                }

                String responseBody = responseStringBuilder.toString();
                ClovaResDto clovaResDto = objectMapper.readValue(responseBody, ClovaResDto.class);
                return clovaResDto;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 실패한 응답 처리 (에러 코드를 출력하거나 예외를 던지는 등)
        }

        return null;
    }
}
