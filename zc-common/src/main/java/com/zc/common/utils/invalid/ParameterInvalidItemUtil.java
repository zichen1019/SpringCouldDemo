package com.zc.common.utils.invalid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数无效项工具类
 *
 * @author SuperZhouDaLu
 */
public class ParameterInvalidItemUtil {

    public static List<ParameterInvalidItem> convertBindingResultToMapParameterInvalidItemList(BindingResult bindingResult) {
        if (bindingResult == null) {
            return null;
        }

        List<ParameterInvalidItem> parameterInvalidItemList = new ArrayList<>();

        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        fieldErrorList.forEach(fieldError -> {
            ParameterInvalidItem parameterInvalidItem = new ParameterInvalidItem();
            parameterInvalidItem.setFieldName(fieldError.getField());
            parameterInvalidItem.setMessage(fieldError.getDefaultMessage());
            parameterInvalidItemList.add(parameterInvalidItem);
        });

        return parameterInvalidItemList;
    }

}
