package com.zc.login.service;

import com.zc.common.model.po.user.User;
import com.zc.common.model.vo.BaseVO;

/**
 * @author zichen
 */
public interface IUserService {

    /**
     * 认证用户信息
     * @param user
     * @return
     */
    String auth(User user);

}
