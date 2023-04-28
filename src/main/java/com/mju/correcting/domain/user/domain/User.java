package com.mju.correcting.domain.user.domain;

import com.mju.correcting.domain.chat_room.domain.ChatRoom;
import com.mju.correcting.domain.game_score.domain.GameScore;
import com.mju.correcting.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "status='ACTIVE'")
@Entity(name = "user")
public class User extends BaseEntity{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Id
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Column(nullable = false)
    private String email;

    @Column(length = 20, name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GameScore> gameScore = new ArrayList<>();
}
