package com.mju.correcting.domain.chat_room.dto;

import com.mju.correcting.domain.chat_room.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatRoomRes {
    private Long chatRoomId;
    private String name;
    private String category;

    public ChatRoomRes(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getId();
        this.name = chatRoom.getName();
        this.category = chatRoom.getInterest().name();
    }
}
