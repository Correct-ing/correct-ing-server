package com.mju.correcting.domain.test.dto;

import com.mju.correcting.domain.chat.Category;
import lombok.Data;

@Data
public class PostTestReq {

    private Category category;

    private String question;

    private String answer;

    private String userAnswer;
}
