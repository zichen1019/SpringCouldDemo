package com.zc.common.exception;

import lombok.Getter;

/**
 * @author zichen
 */
@Getter
public class BaseException extends RuntimeException {
    /**
     * 异常对应的错误类型
     */
    private final ErrorType errorType;

    /**
     * 默认是系统异常
     */
    public BaseException() {
        this.errorType = SystemErrorType.SYSTEM_ERROR;
    }

    public BaseException(ErrorType errorType) {
        this.errorType = errorType;
    }

}
