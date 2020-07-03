package com.zc.login.controller;

import com.zc.common.model.dto.UserDTO;
import com.zc.common.model.vo.BaseVO;
import com.zc.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zichen
 */
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 认证用户信息
     * @param userDTO
     * @return
     */
    @GetMapping("/login")
    public BaseVO login(UserDTO userDTO) {
        String token = userService.login(userDTO);
        if (token == null) {
            return BaseVO.fail();
        } else {
            return BaseVO.success(token);
        }
    }

    @GetMapping("/insert")
    public int insert() {
        return userService.insert();
    }

}
