package com.zc.common.utils.invalid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数无效项
 *
 * @author zichen
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParameterInvalidItem {

	/**
	 * 无效字段的名称
	 */
	private String fieldName;

	/**
	 * 错误信息
	 */
	private String message;

}
