package com.manneron.manneron.chat.service;

import com.manneron.manneron.chat.dto.ClovaReqDto;
import com.manneron.manneron.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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

    private final ChatRepository chatRepository;
    private final ChatService chatService;

    public void sendHttpRequest(ClovaReqDto clovaReqDto){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
        headers.set("X-NCP-APIGW-API-KEY", apiGWKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<ClovaReqDto> requestEntity = new HttpEntity<>(clovaReqDto, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                clovaURL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
        if(statusCode == HttpStatus.OK){
            String responseBody = responseEntity.getBody();
        }else{
            log.error("HTTP 요청이 실패했습니다. 상태 코드: {}", statusCode);
            throw new RuntimeException("HTTP 요청이 실패했습니다.");
        }

    }

    // Clova Studion에 답변 요청
//    @Transactional
//    public String getClovaReply(Chatroom chatroom, String question) throws IOException {
//
//        // Clova Studio에 연결
//        String apiURL = clovaURL;
//        URL url = new URL(apiURL);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
//        con.setRequestProperty("X-NCP-APIGW-API-KEY", apiGWKey);
//        con.setRequestProperty("Content-Type", contentType);
//
//        // 데이터 전송
//        con.setDoOutput(true);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonReq = objectMapper.writeValueAsString(clovaReqDto);
//        try (OutputStream os = con.getOutputStream();
//             OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
//            osw.write(jsonReq);
//            osw.flush();
//        }
//
//        // 응답 코드 확인
//        int responseCode = con.getResponseCode();
//        System.out.println("HTTP 응답 코드: " + responseCode);
//
//        // 응답 메시지 확인
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
//                String line;
//                StringBuilder response = new StringBuilder();
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                System.out.println("응답 바디: " + response.toString());
//            }
//        } else {
//            System.out.println("에러 응답: " + responseCode);
//        }
//
//        con.disconnect();
//
//        // 답변 저장
//        String answer = "";
//        chatService.saveChat(chatroom, answer, "assistant");
//
//        return answer;
//    }
}
