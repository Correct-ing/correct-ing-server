package com.mju.correcting.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostUserReq {

    private String id;
    private String name;
    private String password;
}
