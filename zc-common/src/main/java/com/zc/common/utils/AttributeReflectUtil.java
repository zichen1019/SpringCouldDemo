package com.zc.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import com.zc.common.config.enums.ResultCode;
import com.zc.common.exception.BusinessException;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 复制对象工具类
 * 利用反射复制对象属性
 *
 * @author zichen
 */
public final class AttributeReflectUtil {

    /**
     * 通过反射将 parentObj 对象数据赋值到 assignObj 对象中。
     *
     * @param assignObj  被赋值对象
     * @param parentObj  被复制对象
     * @param isCopyNull TRUE： 赋值对象内容为空才会被复制 FALSE ：赋值对象内容会被覆盖
     * @param isCopyPK   TRUE: 复制对象主键 FALSE： 不复制复制对象主键
     * @return assignObj  拿到复制数据后的完整对象
     */
    public static Object copyData(Object assignObj, Object parentObj, boolean isCopyNull, boolean isCopyPK) {
        // 如果被复制的对象为空，则直接将被赋值的对象返回
        if (ObjectUtil.isNull(parentObj)) {
            return assignObj;
        }

        // 获取所有属性，返回Field数组
        Field[] assignFields = ReflectUtil.getFields(assignObj.getClass());
        Field[] parentFields = ReflectUtil.getFields(parentObj.getClass());

        Arrays.stream(assignFields).forEach(assignField -> {
            // 获取属性首字母大写名称
            String upCaseAssignName = getUpCaseFiledName(assignField);
            // 去除不需要复制的属性
            if ((!isCopyPK && upCaseAssignName.equals("Id")) || upCaseAssignName.equals("SerialVersionUID")) return;
            // 获取被赋值对象的内容
            Object assignValue = ReflectUtil.invoke(assignObj, "get" + upCaseAssignName);
            // 如果被赋值对象不为空并且需要赋值对象为空时返回
            if (assignValue != null && isCopyNull) return;
            Arrays.stream(parentFields).forEach(parentField -> {
                // 获取属性类型
                String assignType = assignField.getGenericType().getTypeName();
                // 获取属性首字母大写名称
                String upCaseParentFieldName = getUpCaseFiledName(parentField);
                // 去除不需要复制的属性
                if ((!isCopyPK && upCaseParentFieldName.equals("Id")) || upCaseParentFieldName.equals("SerialVersionUID")) return;
                // 获取被复制对象的值
                Object parentValue = ReflectUtil.invoke(parentObj, "get" + upCaseParentFieldName);
                if (upCaseParentFieldName.equals(upCaseAssignName) && parentValue != null) {
                    switch (assignType) {
                        case "java.lang.String":
                            ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toStr(parentValue));
                            break;
                        case "java.lang.Integer":
                            ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toInt(parentValue));
                            break;
                        case "java.lang.Long":
                            ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toLong(parentValue));
                            break;
                        case "java.lang.Float":
                            ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toFloat(parentValue));
                            break;
                        case "java.lang.Double":
                            ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toDouble(parentValue));
                            break;
                        case "java.util.Date":
                            ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toDate(parentValue));
                            break;
                        case "java.lang.Boolean":
                            ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toBool(parentValue));
                            break;
	                    case "java.math.BigDecimal":
		                    ReflectUtil.invoke(assignObj, "set" + upCaseAssignName, Convert.toBigDecimal(parentValue));
		                    break;
                        default:
                            throw new BusinessException(ResultCode.UNDEFINED_FIELD_TYPE_ERROR, assignType);
                    }
                }
            });
        });

        return assignObj;
    }

    /**
     * 将数据库拉取的值对应到实体类的Column字段。
     * 如列名Column=s1；Column=s1；
     * 如别名String s1；String baogaobianhao；
     *
     * @param tableObj        数据库实体类表
     * @param entityObj       实体类
     * @param isEntityToTable true: 对象转换数据库字段 false:数据库字段转换对象
     * @param isCopyNull TRUE： 实体类内容为空才会被复制 FALSE ：实体类内容会被覆盖
     * @return 实体类/数据库实体类表
     */
    public static Object transformTableData(Object tableObj, Object entityObj, boolean isEntityToTable, boolean isCopyNull) {
        if (ObjectUtil.isNull(tableObj) || ObjectUtil.isNull(entityObj)) {
            return null;
        }
        // 获取实体类的所有属性，返回Field数组
        Field[] tableFields = ReflectUtil.getFields(tableObj.getClass());
        Field[] entityFields = ReflectUtil.getFields(entityObj.getClass());

        Arrays.stream(tableFields).forEach(tableField -> {
            // 获取属性首字母大写名称
            String upCaseTableName = getUpCaseFiledName(tableField);
            if (upCaseTableName.equals("SerialVersionUID")) return;
            // 获取数据库对象属性值
            Object tableValue = ReflectUtil.invoke(tableObj, "get" + upCaseTableName);
            // 如果数据库内容为空，并且类型不是实体类转换成数据库对象时，直接返回，结束当前循环
            if (tableValue == null && !isEntityToTable) return;
            Arrays.stream(entityFields).forEach(entityField -> {
                String entityType = entityField.getGenericType().getTypeName();
                // 获取Column列
                Column entityFieldAnnotation = entityField.getAnnotation(Column.class);
                // 获取首字母大写后的列名称
                String entityFieldAnnotationName = entityFieldAnnotation.name().replaceFirst(entityFieldAnnotation.name().substring(0, 1),
                    entityFieldAnnotation.name().substring(0, 1).toUpperCase());
                // 获取属性首字母大写名称
                String upCaseEntityName = getUpCaseFiledName(entityField);
                // 获取实体类对象的值
                Object entityValue = ReflectUtil.invoke(entityObj, "get" + upCaseEntityName);
                // 如果实体类内容为空，并且类型是实体类转换成数据库对象时，直接返回，结束当前循环
                if (entityValue == null && isEntityToTable) return;
                // 如果实体类不为空并且isCopyNull为true并且isEntityToTable为false返回
                if ((entityValue != null && !"".equals(entityValue)) && isCopyNull && !isEntityToTable) return;
                Object resultValue = isEntityToTable ? entityValue : tableValue;
                if (upCaseTableName.equals(entityFieldAnnotationName)) {
                    switch (entityType) {
                        case "java.lang.String":
                            ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
                                "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
                                Convert.toStr(resultValue));
                            break;
                        case "java.lang.Integer":
                            ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
                                "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
                                Convert.toInt(resultValue));
                            break;
                        case "java.lang.Long":
                            ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
                                "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
                                Convert.toLong(resultValue));
                            break;
                        case "java.lang.Float":
                            ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
                                "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
                                Convert.toFloat(resultValue));
                            break;
                        case "java.lang.Double":
                            ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
                                "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
                                Convert.toDouble(resultValue));
                            break;
                        case "java.util.Date":
                            ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
                                "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
                                Convert.toDate(resultValue));
                            break;
                        case "java.lang.Boolean":
                            ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
                                "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
                                Convert.toBool(resultValue));
                            break;
	                    case "java.math.BigDecimal":
		                    ReflectUtil.invoke(isEntityToTable ? tableObj : entityObj,
			                    "set" + (isEntityToTable ? upCaseTableName : upCaseEntityName),
			                    Convert.toBigDecimal(resultValue));
		                    break;
                        default:
                            throw new BusinessException(ResultCode.UNDEFINED_FIELD_TYPE_ERROR, entityType);
                    }
                }
            });
        });

        return isEntityToTable ? tableObj : entityObj;
    }

    /**
     * 通过Field获取首字母大写的属性名称
     *
     * @param field Field对象
     * @return 首字母大写属性名称
     */
    private static String getUpCaseFiledName(Field field) {
        // 获取属性的名字
        String fieldName = field.getName();
        // 将属性的首字母大写
        fieldName = fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
        return fieldName;
    }

}