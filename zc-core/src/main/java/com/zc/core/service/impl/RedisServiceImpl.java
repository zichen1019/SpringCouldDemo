package com.zc.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zc.common.model.po.user.User;
import com.zc.common.utils.redis.RedisHelper;
import com.zc.common.model.bo.RedisInfo;
import com.zc.common.model.dto.PageDTO;
import com.zc.core.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zichen
 */
@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 获取redis中的数据
     *
     * @param pageDTO   分页对象
     * @param redisDTO  条件查询
     * @return          redis数据
     */
    @Override
    public List<RedisInfo> list(PageDTO pageDTO, RedisInfo redisDTO) {
        List<RedisInfo> redisInfoList = new ArrayList<>();
        String searchKey = StrUtil.isEmpty(redisDTO.getKey()) ? "*" : "*" + redisDTO.getKey() + "*";
        List<String> searchKeys = CollUtil.newArrayList(RedisHelper.keys(searchKey));
        CollUtil.page(pageDTO.getPageNum(), pageDTO.getPageSize(), searchKeys).forEach(key -> {
            RedisInfo redisVo = new RedisInfo(key, RedisHelper.getJSONSerializer(key, User.class));
            redisInfoList.add(redisVo);
        });
        return redisInfoList;
    }

}
