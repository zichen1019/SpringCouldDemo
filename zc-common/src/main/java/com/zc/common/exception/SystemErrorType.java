package com.zc.common.exception;

import lombok.Getter;

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_ERROR(-1, "系统异常"),
    SYSTEM_BUSY(1, "系统繁忙,请稍候再试"),

    UNAUTHORIZED(401,"没有权限"),
    AUTH_EXPIRED(3000,"认证到期"),

    GATEWAY_NOT_FOUND_SERVICE(10404, "服务未找到"),
    GATEWAY_ERROR(10500, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(10002, "网关超时"),

    ARGUMENT_NOT_VALID(20000, "请求参数校验不通过"),
    INVALID_TOKEN(20001, "无效token"),
    UPLOAD_FILE_SIZE_LIMIT(20010, "上传文件大小超过限制"),

    DUPLICATE_PRIMARY_KEY(30000,"唯一键冲突");

    /**
     * 错误类型码
     */
    private Integer code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    SystemErrorType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
