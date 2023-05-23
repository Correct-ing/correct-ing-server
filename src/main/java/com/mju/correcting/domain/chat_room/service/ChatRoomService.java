package com.mju.correcting.domain.chat_room.service;

import com.mju.correcting.domain.chat_room.domain.ChatRoom;
import com.mju.correcting.domain.chat_room.dto.ChatRoomRes;
import com.mju.correcting.domain.chat_room.dto.PostChatRoomReq;
import com.mju.correcting.domain.chat_room.repository.ChatRoomRepository;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.global.common.error.BaseCode;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createChatRoom(PostChatRoomReq postChatRoomReq) {

        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));


        return chatRoomRepository.save(ChatRoom.builder()
                .name(postChatRoomReq.getName())
                .interest(postChatRoomReq.getCategory())
                .user(user)
                .build()).getId();
    }

    public List<ChatRoomRes> getChatRooms() {

        User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));


        List<ChatRoom> byUser = chatRoomRepository.findByUser(user);

        return byUser.stream()
                .map(ChatRoomRes::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteChatRoom(Long chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
    }
}
