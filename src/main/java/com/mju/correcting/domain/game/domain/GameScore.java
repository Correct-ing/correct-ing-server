package com.mju.correcting.domain.game.domain;

import com.mju.correcting.domain.game.domain.Game;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Builder
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "status='ACTIVE'")
@Entity(name = "game-score")
public class GameScore extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_score_id")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column
    private Integer score;
}
