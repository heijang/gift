package com.practice.gift.common.exception;

import com.practice.gift.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException() {
        super(ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String errorMsg) {
        super(errorMsg, ErrorCode.COMMON_ENTITY_NOT_FOUND);
    }
}
