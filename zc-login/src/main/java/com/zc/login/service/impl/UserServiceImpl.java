package com.zc.login.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zc.common.exception.ServiceException;
import com.zc.common.exception.SystemErrorType;
import com.zc.common.model.dto.UserDTO;
import com.zc.common.model.po.user.User;
import com.zc.common.utils.JwtUtil;
import com.zc.login.mapper.UserMapper;
import com.zc.login.service.UserService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (StrUtil.isEmpty(userDTO.getUserName())) {
            throw new ServiceException(SystemErrorType.USER_USERNAME_IS_NOT_NULL);
        }
        if (StrUtil.isEmpty(userDTO.getPassword())) {
            throw new ServiceException(SystemErrorType.USER_PASSWORD_IS_NOT_NULL);
        }
        User user = userMapper.selectOne(User.builder().userName(userDTO.getUserName()).password(userDTO.getPassword()).build());
        if (user != null) {
            return JwtUtil.buildJwt(user, secretKey);
        }
        return null;
    }

    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public int insert() {
        return userMapper.insertSelective(User.builder().userName("test2").build());
    }

}
