package com.mju.correcting.global.chat_gpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mju.correcting.global.chat_gpt.dto.ChatGptRequestDto;
import com.mju.correcting.global.chat_gpt.dto.ChatGptResponseDto;
import com.mju.correcting.global.chat_gpt.dto.QuestionRequestDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatGptService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatGptRequestDto> buildHttpEntity(ChatGptRequestDto requestDto) {
        String s = null;
        try {
            s = objectMapper.writeValueAsString(requestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity(s, headers);
    }

    public ChatGptResponseDto getResponse(HttpEntity httpEntity) {
        objectMapper.registerModule(new JavaTimeModule());
        ResponseEntity responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                httpEntity,
                String.class);
        return objectMapper.convertValue(responseEntity.getBody(), ChatGptResponseDto.class);
    }

    public ChatGptResponseDto askQuestion(QuestionRequestDto requestDto) {
        ChatGptResponseDto response = this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDto(
                                ChatGptConfig.MODEL,
                                requestDto.getQuestion(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );

        return response;
    }
}