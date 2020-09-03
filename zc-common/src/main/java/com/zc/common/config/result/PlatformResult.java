package com.zc.common.config.result;

import com.zc.common.config.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回结果
 *
 * @author zichen
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlatformResult implements Result {

    private static final long serialVersionUID = 874200365941306385L;

    private Integer code;

    private String message;

    private Object data;

    public static PlatformResult success() {
        PlatformResult result = new PlatformResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static PlatformResult success(Object data) {
        PlatformResult result = new PlatformResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static PlatformResult failure(ResultCode resultCode) {
        PlatformResult result = new PlatformResult();
        result.setResultCode(resultCode);
        return result;
    }

    public static PlatformResult failure(ResultCode resultCode, Object data) {
        PlatformResult result = new PlatformResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static PlatformResult failure(String message) {
        PlatformResult result = new PlatformResult();
        result.setCode(ResultCode.PARAM_IS_INVALID.getCode());
        result.setMessage(message);
        return result;
    }

    private void setResultCode(ResultCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

}
