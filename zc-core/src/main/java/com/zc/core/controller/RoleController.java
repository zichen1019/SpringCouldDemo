package com.zc.core.controller;

import com.zc.core.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zichen
 */
@RequiredArgsConstructor
@RequestMapping("/role")
@RestController
public class RoleController {

    private final RoleService roleService;

    /**
     * 认证用户信息
     * @return
     */
    @GetMapping("/insert")
    public String insert() {
        return roleService.insert();
    }

}
