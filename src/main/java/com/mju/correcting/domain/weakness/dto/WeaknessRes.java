package com.mju.correcting.domain.weakness.dto;

import com.mju.correcting.domain.chat.Category;
import com.mju.correcting.domain.weakness.domain.Weakness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeaknessRes {

    private String category;

    private String wrongSentence;

    private String correctSentence;

    public WeaknessRes(Weakness weakness) {
        this.category = weakness.getCategory().name();
        this.wrongSentence = weakness.getWrongSentence();
        this.correctSentence = weakness.getCorrectSentence();
    }
}
