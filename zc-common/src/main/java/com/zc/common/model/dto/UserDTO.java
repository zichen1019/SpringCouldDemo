package com.zc.common.model.dto;

import lombok.Data;

/**
 * @author zichen
 */
@Data
public class UserDTO {

    /**
     * 用户登录账号
     */
    private String userName;

    /**
     * 用户登录密码
     */
    private String password;

}
