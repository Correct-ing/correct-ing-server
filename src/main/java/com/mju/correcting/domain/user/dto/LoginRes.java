package com.mju.correcting.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginRes {

    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;
    private String registerStatus;
    private Long userId;

    @Builder
    public LoginRes(String grantType, String accessToken, Long accessTokenExpiresIn, String refreshToken, String registerStatus, Long userId) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshToken = refreshToken;
        this.registerStatus = registerStatus;
        this.userId = userId;
    }
}
