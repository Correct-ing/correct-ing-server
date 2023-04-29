package com.mju.correcting.global.common.exception;

import com.mju.correcting.global.common.error.BaseCode;

public class CustomException extends RuntimeException {

    private BaseCode baseCode;

    public CustomException(String message, BaseCode baseCode) {
        super(message);
        this.baseCode = baseCode;
    }

    public CustomException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }

    public BaseCode getErrorCode() {
        return baseCode;
    }
}
