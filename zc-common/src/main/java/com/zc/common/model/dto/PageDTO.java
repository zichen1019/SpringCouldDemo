package com.zc.common.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询对象
 *
 * @author zhoujl
 * @date 2019-04-30
 */
@ApiModel(description = "分页查询对象")
@Data
public class PageDTO {

    /**
     * 按创建时间倒序排序
     */
    public static final String ORDER_BY_ID_DESC = "id desc";

    @ApiModelProperty(value = "当前页号")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "一页数量")
    private Integer pageSize = 20;

    @ApiModelProperty(value = "排序", notes = "例：create_time desc,update_time desc")
    private String orderBy;

}
