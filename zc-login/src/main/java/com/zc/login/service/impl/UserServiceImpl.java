package com.zc.login.service.impl;

import com.zc.common.model.dto.UserDTO;
import com.zc.common.model.po.user.User;
import com.zc.common.utils.JwtUtil;
import com.zc.login.mapper.UserMapper;
import com.zc.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zichen
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Value("${secret_key}")
    private String secretKey;

    /**
     * 认证用户信息
     * @param userDTO
     * @return
     */
    @Override
    public String login(UserDTO userDTO) {
        assert userDTO.getUserName() != null;
        assert userDTO.getPassword() != null;
        User user = userMapper.selectOne(User.builder().userName(userDTO.getUserName()).password(userDTO.getPassword()).build());
        if (user != null) {
            return JwtUtil.buildJwt(user, secretKey);
        }
        return null;
    }

}
