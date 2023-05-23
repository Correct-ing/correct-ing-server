package com.mju.correcting.domain.chat_log.repository;

import com.mju.correcting.domain.chat_log.domain.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    List<ChatLog> findByChatRoomId(Long chatRoomId);

    List<ChatLog> findFirst3ByChatRoomId(Long chatRoomId);
}
