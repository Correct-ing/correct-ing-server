package com.mju.correcting.domain.chat_room.controller;

import com.mju.correcting.domain.chat_room.dto.ChatRoomRes;
import com.mju.correcting.domain.chat_room.dto.PostChatRoomReq;
import com.mju.correcting.domain.chat_room.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-rooms")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("")
    public ResponseEntity<String> createChatRoom(@RequestBody PostChatRoomReq postChatRoomReq) {
        return ResponseEntity.created(URI.create("/api/v1/users/" + chatRoomService.createChatRoom(postChatRoomReq))).build();
    }

    @GetMapping("")
    public ResponseEntity<List<ChatRoomRes>> getChatRooms() {
        return ResponseEntity.ok(chatRoomService.getChatRooms());
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<String> deleteChatRoom(@PathVariable Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        return ResponseEntity.ok("");
    }
}
