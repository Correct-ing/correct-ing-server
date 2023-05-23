package com.mju.correcting.domain.chat_room.repository;

import com.mju.correcting.domain.chat_room.domain.ChatRoom;
import com.mju.correcting.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByUser(User user);
}
