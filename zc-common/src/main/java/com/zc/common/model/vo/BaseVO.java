package com.zc.common.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zc.common.exception.BaseException;
import com.zc.common.exception.ErrorType;
import com.zc.common.exception.SystemErrorType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author zichen
 */
@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
@Getter
public class BaseVO<T> {

    public static final int SUCCESSFUL_CODE = 200;
    public static final String SUCCESSFUL_MESG = "处理成功";

    @ApiModelProperty(value = "处理结果code", required = true)
    private Integer code;

    @ApiModelProperty(value = "处理结果描述信息")
    private String msg;

    @ApiModelProperty(value = "处理结果数据信息")
    private T data;

    /**
     * @param errorType
     */
    public BaseVO(ErrorType errorType) {
        this.code = errorType.getCode();
        this.msg = errorType.getMsg();
    }

    /**
     * @param errorType
     * @param data
     */
    public BaseVO(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    private BaseVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return BaseVO
     */
    public static BaseVO success(Object data) {
        return new BaseVO<>(SUCCESSFUL_CODE, SUCCESSFUL_MESG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return BaseVO
     */
    public static BaseVO success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return BaseVO
     */
    public static BaseVO fail() {
        return new BaseVO(SystemErrorType.SYSTEM_ERROR);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @param baseException
     * @return BaseVO
     */
    public static BaseVO fail(BaseException baseException) {
        return fail(baseException, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return BaseVO
     */
    public static BaseVO fail(BaseException baseException, Object data) {
        return new BaseVO<>(baseException.getErrorType(), data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @param data
     * @return BaseVO
     */
    public static BaseVO fail(ErrorType errorType, Object data) {
        return new BaseVO<>(errorType, data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @return BaseVO
     */
    public static BaseVO fail(ErrorType errorType) {
        return BaseVO.fail(errorType, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return BaseVO
     */
    public static BaseVO fail(Object data) {
        return new BaseVO<>(SystemErrorType.SYSTEM_ERROR, data);
    }


    /**
     * 成功code=000000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.code == SUCCESSFUL_CODE;
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }
}
