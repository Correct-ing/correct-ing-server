package com.mju.correcting.domain.game.controller;

import com.mju.correcting.domain.game.service.GameScoreService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;가
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Game", description = "게임 API")
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
@RestController
public class GameScoreController {

    private final GameScoreService gameScoreService;

    @PostMapping("")
    public ResponseEntity<String> postScore(@RequestParam int score) {
        gameScoreService.postScore(score);
        return ResponseEntity.ok("");
    }
}
