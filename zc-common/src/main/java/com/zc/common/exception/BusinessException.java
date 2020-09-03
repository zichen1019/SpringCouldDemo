package com.zc.common.exception;

import com.zc.common.config.enums.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 *
 * @author zichen
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 194906846739586856L;

    private ResultCode resultCode;

    protected Object data;

    public BusinessException(ResultCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

}
