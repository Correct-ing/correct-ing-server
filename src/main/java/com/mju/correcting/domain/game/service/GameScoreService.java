package com.mju.correcting.domain.game.service;

import com.mju.correcting.domain.game.domain.GameScore;
import com.mju.correcting.domain.game.repository.GameScoreRepository;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.global.common.error.BaseCode;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GameScoreService {

    private final GameScoreRepository gameScoreRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postScore(int score) {
        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

        gameScoreRepository.save(GameScore.builder()
                .user(user)
                .score(score)
                .build());
    }
}
