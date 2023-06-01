package com.mju.correcting.domain.game.controller;

import com.mju.correcting.domain.game.dto.GetScore;
import com.mju.correcting.domain.game.service.GameScoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Game", description = "게임 API")
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
@RestController
public class GameController {

    private final GameScoreService gameScoreService;

    @PostMapping("")
    public ResponseEntity<String> postScore(@RequestParam int score) {
        gameScoreService.postScore(score);
        return ResponseEntity.ok("");
    }

    @GetMapping("/me")
    public ResponseEntity<GetScore> getScore() {
        return ResponseEntity.ok(gameScoreService.getScore());
    }
}
