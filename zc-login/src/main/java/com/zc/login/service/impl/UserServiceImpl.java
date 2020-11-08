package com.zc.login.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import com.zc.common.config.enums.ResultCode;
import com.zc.common.config.request.RequestContextUtil;
import com.zc.common.exception.BusinessException;
import com.zc.common.model.dto.user.AddUserDTO;
import com.zc.common.model.dto.user.UserDTO;
import com.zc.common.model.po.user.User;
import com.zc.common.model.vo.user.UserVO;
import com.zc.common.utils.AttributeReflectUtil;
import com.zc.common.config.redis.RedisHelper;
import com.zc.login.mapper.UserMapper;
import com.zc.login.service.UserService;
import com.zc.login.util.OnLineUserCountUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zichen
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    /**
     * 认证用户信息
     * @param userDTO
     * @return
     */
    @Override
    public String login(UserDTO userDTO) {
        if (StrUtil.isEmpty(userDTO.getUserName())) {
            throw new BusinessException(ResultCode.USER_USERNAME_IS_NOT_NULL);
        }
        if (StrUtil.isEmpty(userDTO.getPassword())) {
            throw new BusinessException(ResultCode.USER_PASSWORD_IS_NOT_NULL);
        }
        User user = userMapper.selectOne(User.builder().userName(userDTO.getUserName()).build());
        if (ObjectUtil.isNotNull(user)) {
            Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA).setPublicKey(KeyUtil.generateRSAPublicKey(Base64.decode(user.getPublicKey())));
            if (sign.verify(StrUtil.bytes(userDTO.getPassword()), Base64.decode(user.getPassword()))) {
                String token = IdUtil.fastSimpleUUID();
                RedisHelper.addJSONSerializer(HttpHeaders.AUTHORIZATION + ":" + token, user, 30 * 1000);
                OnLineUserCountUtil.add();
                return token;
            }
            throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }
        throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }

    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public int insert(AddUserDTO addUserDTO) {
        User user = (User) AttributeReflectUtil.copyData(User.builder().build(), addUserDTO, false, false);
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);
        user.setPublicKey(sign.getPublicKeyBase64());
        user.setPassword(Base64.encode(sign.sign(StrUtil.bytes(user.getPassword()))));
        return userMapper.insertSelective(user);
    }

    @Override
    public UserVO get(UserDTO userDTO) {
        User user = userMapper.selectOne((User) AttributeReflectUtil.copyData(User.builder().build(), userDTO, true, false));
        return (UserVO) AttributeReflectUtil.copyData(new UserVO(), user, false, true);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        RedisHelper.delKey(HttpHeaders.AUTHORIZATION + ":" + RequestContextUtil.getRequest().getHeader(HttpHeaders.AUTHORIZATION));
        OnLineUserCountUtil.reduce();
    }

}
