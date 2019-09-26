package com.mbyte.easy.common.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 申劭明
 */
@ControllerAdvice
public class ControllerStringParamTrimConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 创建 String trim 编辑器
        // 构造方法中 boolean 参数含义为如果是空白字符串,是否转换为null
        // 即如果为true,那么 " " 会被转换为 null,否者为 ""
        StringTrimmerEditor propertyEditor = new StringTrimmerEditor(true);
        // 为 String 类对象注册编辑器
        binder.registerCustomEditor(String.class, propertyEditor);

        /**
         * 将前台传递过来的日期格式的字符串，自动转化为时间类型
         */
        // LocalDateTime 类型转换
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (StringUtils.isNoneBlank(text)) {
                    setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            }
        });
        // LocalDate 类型转换
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                if(StringUtils.isNoneBlank(text)){
                    setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
            }
        });
    }
}