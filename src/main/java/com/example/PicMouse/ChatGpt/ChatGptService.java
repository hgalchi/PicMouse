package com.example.PicMouse.ChatGpt;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.api.client.json.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ChatGptService {

    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public Map<String, String> getResponse(HttpEntity<ChatGptRequestDto> chatGptRequestDtoHttpEntity) {

        ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                JsonNode.class);

        String[] list=responseEntity.getBody().get("choices").get(0).get("message").get("content").toString().split("\\\\n");

        Map<String, String> map = new HashMap<>();
        for (String str : list) {
            map.put(str.split(":")[0], str.split(":")[1]);
        }

        System.out.println(map);
        System.out.println();
        return map;
    }

    public Map<String, String> askQuestion(QuestionRequest request) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDto(
                                ChatGptConfig.MODEL,
                                request.getMessages(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
    }
}
