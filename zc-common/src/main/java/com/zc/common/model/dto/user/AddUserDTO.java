package com.zc.common.model.dto.user;

import lombok.Data;

/**
 * @author zichen
 */
@Data
public class AddUserDTO {

    /**
     * 用户登录账号
     */
    private String userName;

    /**
     * 用户登录密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

}
