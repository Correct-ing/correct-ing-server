package com.mju.correcting.domain.chat.domain;

import com.mju.correcting.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Getter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "status='ACTIVE'")
@Entity(name = "chat_log")
public class ChatLog extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_log_id")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Column(length = 300, nullable = false)
    private String question;

    @Column(length = 300, nullable = false)
    private String answer;
}
