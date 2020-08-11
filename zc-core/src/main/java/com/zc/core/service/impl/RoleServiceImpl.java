package com.zc.core.service.impl;

import com.zc.common.model.po.user.UserRole;
import com.zc.core.feign.LoginFeignClient;
import com.zc.core.mapper.UserRoleMapper;
import com.zc.core.service.RoleService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zichen
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private LoginFeignClient loginFeignClient;

    /**
     * 认证用户信息
     * @return
     */
    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public String insert() {
        userRoleMapper.insertSelective(UserRole.builder().userId(1).roleId(1).build());
        loginFeignClient.insert();
        Integer.parseInt("456我是");
        return null;
    }

}
