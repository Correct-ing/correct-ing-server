package com.mju.correcting.domain.game.dto;

import com.mju.correcting.domain.game.domain.GameScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserScoreDto {
    private Long id;
    private int score;
    private String name;
    public UserScoreDto(GameScore gameScore) {
        this.id = gameScore.getId();
        this.score = gameScore.getScore();
        this.name = gameScore.getUser().getUsername();
    }
}
