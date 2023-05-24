package com.mju.correcting.domain.weakness.controller;

import com.mju.correcting.domain.weakness.dto.GetWeakNessRes;
import com.mju.correcting.domain.weakness.service.WeaknessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/weakness")
@RestController
public class WeaknessController {

    private final WeaknessService weaknessService;

    @GetMapping("/me")
    public ResponseEntity<GetWeakNessRes> getWeakness() {
        return ResponseEntity.ok(weaknessService.getWeakness());
    }
}
