package com.mju.correcting.domain.chat_room.controller;

import com.mju.correcting.domain.chat_room.dto.PostChatRoomReq;
import com.mju.correcting.domain.chat_room.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chat-rooms")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("")
    public ResponseEntity<String> createChatRoom(@RequestBody PostChatRoomReq postChatRoomReq) {
        return ResponseEntity.created(URI.create("/api/v1/users/"+chatRoomService.createChatRoom(postChatRoomReq))).build();
    }
}
