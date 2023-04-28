package com.mju.correcting.domain.game.domain;
import com.mju.correcting.domain.game_problem.domain.GameProblem;
import com.mju.correcting.domain.game_score.domain.GameScore;
import com.mju.correcting.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "status='ACTIVE'")
@Entity(name = "game")
public class Game extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    @Id
    private Long id;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @Column(length = 30, nullable = false)
    private String name;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<GameProblem> gameProblems = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<GameScore> gameScore = new ArrayList<>();
}
