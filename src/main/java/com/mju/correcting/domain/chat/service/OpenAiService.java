package com.mju.correcting.domain.chat.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class OpenAiService {
    private static final String API_KEY = "sk-";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String getCompletion(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // 프롬프트 설정 + 이전 대화 상황
        List<String> previousMessages = new ArrayList<>();
        String p = "We are engaged in a conversation in English within a set context. Please correct any incorrect English expressions and say '대화 상황에 맞는 입력을 해주세요' if I get off from context";
        previousMessages.add(p);

        // 영어 필터링
        if (!isEnglish(prompt)) {
            return "대화 상황에 맞는 입력을 해주세요.";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + API_KEY);

        StringBuilder messagesJson = new StringBuilder("[");
        for (int i = 0; i < previousMessages.size(); i++) {
            messagesJson.append("{\"role\": \"user\", \"content\": \"")
                    .append(previousMessages.get(i))
                    .append("\"}");
            if (i < previousMessages.size() - 1) {
                messagesJson.append(",");
            }
        }
        messagesJson.append(",{\"role\": \"user\", \"content\": \"").append(prompt).append("\"}]");

        String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": " + messagesJson.toString() + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            String content = message.getString("content");
            return content;
        } else {
            throw new RuntimeException("Failed to call OpenAI API: " + response.getStatusCode());
        }
    }

    private boolean isEnglish(String text) {
        Pattern englishPattern = Pattern.compile("[A-Za-z0-9.,?!'\" ]+");
        return englishPattern.matcher(text).matches();
    }
}
