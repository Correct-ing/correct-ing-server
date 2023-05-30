package com.mju.correcting.domain.user.domain;

import com.mju.correcting.domain.chat.domain.ChatRoom;
import com.mju.correcting.domain.game.domain.GameScore;
import com.mju.correcting.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Where(clause = "status='ACTIVE'")
@Entity(name = "user")
public class User extends BaseEntity implements UserDetails {

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

    @Column(length = 40, nullable = false)
    private String name;

    @Column(length = 20, name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GameScore> gameScore = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
