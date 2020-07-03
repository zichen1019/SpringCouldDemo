package com.zc.core.base;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 公用Mapper接口
 *
 * @author zhoujl
 * @date 2018-06-25
 */
@Repository
public interface BaseMapper<T> extends Mapper<T> {
    // FIXME 特别注意，该接口不能被扫描到，否则会出错
}
