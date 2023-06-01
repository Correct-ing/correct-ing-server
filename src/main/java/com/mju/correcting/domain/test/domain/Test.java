package com.mju.correcting.domain.test.domain;

import com.mju.correcting.domain.chat.Category;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Builder
@Getter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "status='ACTIVE'")
@Entity
public class Test extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    @Id
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private String question;

    private String answer;

    private String userAnswer;
}
