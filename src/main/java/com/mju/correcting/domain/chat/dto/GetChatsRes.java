package com.mju.correcting.domain.chat.dto;

import com.mju.correcting.domain.chat.domain.ChatLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetChatsRes {
    private String question;

    private String answer;

    public GetChatsRes(ChatLog chatLog) {
        this.question = chatLog.getQuestion();
        this.answer = chatLog.getAnswer();
    }
}
