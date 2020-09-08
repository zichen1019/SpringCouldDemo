package com.zc.core.controller;

import com.zc.common.model.bo.RedisInfo;
import com.zc.common.model.dto.PageDTO;
import com.zc.core.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zichen
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    /**
     * 查询Redis缓存信息
     *
     * @param pageDTO   分页对象
     * @param redisDTO  条件查询
     * @return 分页后的对象
     */
    @GetMapping("/list")
    public List<RedisInfo> list(PageDTO pageDTO, RedisInfo redisDTO) {
        return redisService.list(pageDTO, redisDTO);
    }

}
