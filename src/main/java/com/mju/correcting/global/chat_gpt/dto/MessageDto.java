package com.mju.correcting.global.chat_gpt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageDto {
    private String role;
    private String content;

    public MessageDto(String content) {
        this.role = "user";
        this.content = content;
    }
}
