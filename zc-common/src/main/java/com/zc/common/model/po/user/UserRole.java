package com.zc.common.model.po.user;

import com.zc.common.model.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.IdentityDialect;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zichen
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "sys_user_role")
public class UserRole implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @KeySql(dialect = IdentityDialect.MYSQL)
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 用户主键
     */
    private Integer userId;

    /**
     * 权限主键
     */
    private Integer roleId;

}
