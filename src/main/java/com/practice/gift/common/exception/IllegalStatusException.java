package com.practice.gift.common.exception;

import com.practice.gift.common.response.ErrorCode;

public class IllegalStatusException extends BaseException {
    public IllegalStatusException() {
        super(ErrorCode.COMMON_ILLEGAL_STATUS);
    }

    public IllegalStatusException(String errorMsg) {
        super(errorMsg, ErrorCode.COMMON_ILLEGAL_STATUS);
    }
}
