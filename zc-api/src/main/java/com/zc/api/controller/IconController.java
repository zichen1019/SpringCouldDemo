package com.zc.api.controller;

import com.zc.api.utils.icon.IconUtil;
import com.zc.common.config.result.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author zichen
 */
@ResponseResult
@RequestMapping("icon")
@RestController
public class IconController {

    /**
     * 根据网址抓取icon网络地址
     *
     * @return  返回可能的图标url地址
     */
    @GetMapping("/grap")
    public Set<String> grap(String url) {
        return IconUtil.getIcon(url);
    }

}
