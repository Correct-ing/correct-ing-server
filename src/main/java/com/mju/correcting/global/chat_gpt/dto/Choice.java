package com.mju.correcting.global.chat_gpt.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class Choice {
    private MessageDto message;
    private String finish_reason;
    private int index;

    // 생성자, getter, setter 등 생략

    // Inner class for MessageDto

}
