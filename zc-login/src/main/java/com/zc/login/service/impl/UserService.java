package com.zc.login.service.impl;

import com.zc.common.model.po.user.User;
import com.zc.common.utils.JwtUtil;
import com.zc.login.mapper.UserMapper;
import com.zc.login.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserMapper userMapper;

    /**
     * 认证用户信息
     * @param user
     * @return
     */
    @Override
    public String auth(User user) {
        assert user.getUserName() != null;
        assert user.getPassword() != null;
        User user1 = userMapper.select(user).get(0);
        if (user1 != null) {
            return JwtUtil.buildJwt(user1);
        }
        return null;
    }

}
