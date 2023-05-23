package com.mju.correcting.domain.weakness.domain;

import com.mju.correcting.domain.chat.Category;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Getter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "status='ACTIVE'")
@Entity(name = "weakness")
public class Weakness extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weakness_id")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(length = 50, nullable = false)
    private String wrongSentence;

    @Column(length = 50, nullable = false)
    private String correctSentence;
}
