package com.mju.correcting.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginUserReq {

    private String id;
    private String password;
}
