package com.mju.correcting.domain.chat.controller;

import com.mju.correcting.domain.chat.dto.GetChatsRes;
import com.mju.correcting.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chats")
@RestController
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{chatRoomId}")
    public ResponseEntity<String> getCompletion(@RequestParam("prompt") String prompt, @PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatService.getCompletion(chatRoomId, prompt));
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<GetChatsRes>> getChats(@PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatService.getChats(chatRoomId));
    }
}
