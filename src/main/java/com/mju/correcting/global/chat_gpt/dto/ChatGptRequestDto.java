package com.mju.correcting.global.chat_gpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptRequestDto implements Serializable {

    private String model;
    private List<MessageDto> messages = new ArrayList<>();
//    @JsonProperty("max_tokens")
//    private Integer maxTokens;
//    private Double temperature;
//    @JsonProperty("top_p")
//    private Double topP;

    @Builder
    public ChatGptRequestDto(String model, String messages,
                             Integer maxTokens, Double temperature,
                             Double topP) {
        this.model = model;
        this.messages.add(new MessageDto(messages));
//        this.prompt = messages;
//        this.maxTokens = maxTokens;
//        this.temperature = temperature;
//        this.topP = topP;
    }
}
