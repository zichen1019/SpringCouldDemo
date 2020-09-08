package com.zc.login.service;


import com.zc.common.model.dto.user.AddUserDTO;
import com.zc.common.model.dto.user.UserDTO;
import com.zc.common.model.vo.user.UserVO;

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

    int insert(AddUserDTO addUserDTO);

    UserVO get(UserDTO userDTO);

    /**
     * 退出登录
     */
    void logout();
}
