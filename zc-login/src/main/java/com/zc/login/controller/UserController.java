package com.zc.login.controller;

import com.zc.common.config.redis.RedisHelper;
import com.zc.common.config.request.RequestContextUtil;
import com.zc.common.config.result.ResponseResult;
import com.zc.common.model.dto.user.AddUserDTO;
import com.zc.common.model.dto.user.UserDTO;
import com.zc.common.model.vo.user.UserVO;
import com.zc.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zichen
 */
@ResponseResult
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
    public String login(UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @GetMapping
    public UserVO get(UserDTO userDTO) {
        return userService.get(userDTO);
    }

    @PostMapping
    public int add(AddUserDTO addUserDTO) {
        return userService.insert(addUserDTO);
    }

    @RequestMapping(value = "/sessionId", method = RequestMethod.GET)
    public String sessionId() {
        return RequestContextUtil.getSession().getId();
    }

    @RequestMapping(value = "/redis/echo/{key}", method = RequestMethod.GET)
    public String echo(@PathVariable String key) {
        return (String) RedisHelper.get(key);
    }

    @RequestMapping(value = "/redis/add/{key}/{value}", method = RequestMethod.GET)
    public boolean echo(@PathVariable String key, @PathVariable String value) {
        RedisHelper.add(key, value);
        return RedisHelper.exists(key);
    }

}
