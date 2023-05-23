package com.mju.correcting.domain.chat_room.dto;

import com.mju.correcting.domain.user.domain.Interest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostChatRoomReq {

    private String name;

    private Interest category;
}
