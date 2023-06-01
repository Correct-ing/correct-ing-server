package com.mju.correcting.domain.test.dto;

import com.mju.correcting.domain.chat.Category;
import com.mju.correcting.domain.test.domain.Test;
import lombok.Data;

@Data
public class GetTestRes {
    private Category category;

    private String question;

    private String answer;

    private String userAnswer;

    public GetTestRes(Test test) {
        this.category = test.getCategory();
        this.question = test.getQuestion();
        this.answer = test.getAnswer();
        this.userAnswer = test.getUserAnswer();
    }
}
