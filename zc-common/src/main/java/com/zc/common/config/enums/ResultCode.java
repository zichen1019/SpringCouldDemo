package com.zc.common.config.enums;

import lombok.Getter;

/**
 * API 统一返回状态码
 *
 * @author zichen
 */
@Getter
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(1, "操作成功"),

    /* 系统错误：10001-19999 */
    SYSTEM_INNER_ERROR(10001, "系统异常"),

    /* 用户错误：20001-29999*/
    UNAUTHORIZED(20001, "没有权限"),
    AUTH_EXPIRED(20002, "认证到期"),
    USER_USERNAME_IS_NOT_NULL(20003, "用户名不能为空"),
    USER_PASSWORD_IS_NOT_NULL(20004, "用户密码不能为空"),
    USERNAME_OR_PASSWORD_ERROR(20005, "用户名或密码错误"),

    /* 网关错误：30001-39999 */
    GATEWAY_NOT_FOUND_SERVICE(30001, "服务未找到"),
    GATEWAY_ERROR(30002, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(30003, "网关超时"),

    /* 数据错误：40001-499999 */
    RESULT_DATA_NONE(40001, "数据未找到"),
    DATA_ALREADY_EXISTED(40002, "数据已存在"),
    DATA_INSERT_ERROR(40003, "数据保存失败"),
    DATA_UPDATE_ERROR(40004, "数据更新失败"),
    DATA_DELETE_ERROR(40005, "数据删除失败"),

    /* 接口错误：50001-59999 */
    INTERFACE_INNER_INVOKE_ERROR(50001, "内部系统接口调用异常"),
    INTERFACE_OUTER_INVOKE_ERROR(50002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(50003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(50004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(50005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(50006, "接口负载过高"),
    UNDEFINED_FIELD_TYPE_ERROR(50007, "未定义的类型"),

    /* 权限错误：60001-69999 */
    PERMISSION_NO_ACCESS(60001, "无访问权限"),

    /* 定时任务错误：70001-79999 */
    QUARTZ_RUN_ERROR(70001, "定时任务执行失败"),
    QUARTZ_PAUSE_ERROR(70002, "定时任务暂停失败"),
    QUARTZ_RESUME_ERROR(70003, "恢复定时任务失败"),
    QUARTZ_CORN_ERROR(70004, "定时任务cron表达式错误"),

    /* 业务错误：80001-89999 */
    PARAM_IS_INVALID(80001, "参数验证错误");


    @Getter
    private final Integer code;

    @Getter
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
