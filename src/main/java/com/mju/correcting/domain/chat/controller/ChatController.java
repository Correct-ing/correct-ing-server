package com.mju.correcting.domain.chat.controller;

import com.mju.correcting.domain.chat.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
@RestController
public class ChatController {

    private final OpenAiService openAiService;

    @PostMapping("/{chatRoomId}")
    public String getCompletion(@RequestParam("prompt") String prompt, @PathVariable Long chatRoomId) {
        return openAiService.getCompletion(chatRoomId, prompt);
    }
}
