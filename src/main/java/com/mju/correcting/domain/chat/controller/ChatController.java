package com.mju.correcting.domain.chat.controller;

import com.mju.correcting.domain.chat.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RequestMapping("/completion")
@RestController
public class ChatController {

    private final OpenAiService openAiService;

    @PostMapping("")
    public String getCompletion(@RequestParam("prompt") String prompt) {
        return openAiService.getCompletion(prompt);
    }
}
