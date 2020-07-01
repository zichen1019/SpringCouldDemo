package com.zc.common.exception;

public interface ErrorType {
    /**
     * 返回code
     *
     * @return
     */
    Integer getCode();

    /**
     * 返回msg
     *
     * @return
     */
    String getMsg();
}
