package com.mju.correcting.domain.test.controller;

import com.mju.correcting.domain.test.dto.GetTestRes;
import com.mju.correcting.domain.test.dto.PostTestReq;
import com.mju.correcting.domain.test.service.TestService;
import com.mju.correcting.domain.weakness.dto.GetWeakNessRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Test", description = "테스트 API")
@RequestMapping("/api/v1/tests")
@RequiredArgsConstructor
@RestController
public class TestController {
    private final TestService testService;
    @PostMapping("")
    public ResponseEntity<String> postTest(@RequestBody PostTestReq postTestReq) {
        testService.postTest(postTestReq);
        return ResponseEntity.ok("");
    }

    @GetMapping("")
    public ResponseEntity<List<GetTestRes>> getTests() {
        return ResponseEntity.ok(testService.getTests());
    }
}
