package com.zc.login.controller;

import com.zc.common.model.po.user.User;
import com.zc.common.model.vo.BaseVO;
import com.zc.login.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zichen
 */
@RequestMapping("/user")
@RestController
public class UserController {

    private IUserService userService;

    /**
     * 认证用户信息
     * @param user
     * @return
     */
    @PostMapping("/auth")
    public BaseVO auth(User user) {
        String token = userService.auth(user);
        if (token == null) {
            return BaseVO.fail();
        } else {
            return BaseVO.success();
        }
    }

}
