package com.zc.login.service;

import com.zc.common.model.dto.UserDTO;

/**
 * @author zichen
 */
public interface UserService {

    /**
     * 认证用户信息
     * @param userDTO
     * @return
     */
    String login(UserDTO userDTO);

}
