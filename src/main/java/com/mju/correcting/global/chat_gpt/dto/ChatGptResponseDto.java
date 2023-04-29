package com.mju.correcting.global.chat_gpt.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class ChatGptResponseDto implements Serializable {

    private String id;
    private String object;
    private int created;
    private String model;
    private Usage usage;
    private Choice[] choices;

    @Builder
    public ChatGptResponseDto(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatGptResponseDto dto = objectMapper.readValue(json, ChatGptResponseDto.class);
        this.id = dto.getId();
        this.object = dto.getObject();
        this.created = dto.getCreated();
        this.model = dto.getModel();
        this.choices = dto.getChoices();
        this.usage = dto.getUsage();
    }
}