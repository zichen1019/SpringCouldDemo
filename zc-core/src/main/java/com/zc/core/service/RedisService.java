package com.zc.core.service;

import com.zc.common.model.bo.RedisInfo;
import com.zc.common.model.dto.PageDTO;

import java.util.List;

/**
 * @author zichen
 */
public interface RedisService {

    /**
     * 获取redis中的数据
     *
     * @param pageDTO   分页对象
     * @param redisDTO  条件查询
     * @return
     */
    List<RedisInfo> list(PageDTO pageDTO, RedisInfo redisDTO);

}
