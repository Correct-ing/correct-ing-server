package com.mju.correcting.domain.game.repository;

import com.mju.correcting.domain.game.domain.GameScore;
import com.mju.correcting.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
    Optional<GameScore> findByUser(User user);
}
