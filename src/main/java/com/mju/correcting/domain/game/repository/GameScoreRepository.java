package com.mju.correcting.domain.game.repository;

import com.mju.correcting.domain.game.domain.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
}
