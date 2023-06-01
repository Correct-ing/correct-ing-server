package com.mju.correcting.domain.game.service;

import com.mju.correcting.domain.game.domain.GameScore;
import com.mju.correcting.domain.game.dto.GetScore;
import com.mju.correcting.domain.game.dto.UserScoreDto;
import com.mju.correcting.domain.game.repository.GameScoreRepository;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.global.common.error.BaseCode;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public GetScore getScore() {
        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

        List<GameScore> scores = gameScoreRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        for(int i = 0; i < scores.size(); i++) {
            if(scores.get(i).getUser().getId().equals(user.getId())) {
                return GetScore.builder()
                        .id(scores.get(i).getId())
                        .score(scores.get(i).getScore())
                        .rank(i + 1)
                        .build();
            }
        }
        return null;
    }

    public List<UserScoreDto> getScores() {
        List<GameScore> score = gameScoreRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));

        return score.stream()
                .map(UserScoreDto::new)
                .collect(Collectors.toList());
    }
}
