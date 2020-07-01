package com.zc.common.model.po.user;

import com.zc.common.model.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * @author zichen
 *
 * 用户信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "sys_user")
public class User extends BasePO {

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
