package com.mju.correcting.domain.chat_room.repository;

import com.mju.correcting.domain.chat_room.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
