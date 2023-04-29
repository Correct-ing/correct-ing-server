package com.mju.correcting.domain.refresh_token.repository;

import com.mju.correcting.domain.refresh_token.domain.RefreshToken;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByKey(String username);
}
