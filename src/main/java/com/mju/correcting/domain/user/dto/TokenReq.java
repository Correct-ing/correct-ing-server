package com.mju.correcting.domain.user.dto;

import lombok.Data;

@Data
public class TokenReq {

    private String accessToken;

    private String refreshToken;
}
