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
public class GetScore {
    private Long id;
    private int score;
    private int rank;
}
