package org.openokr.application.framework.converter.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * spring mvc json框架对象映射 配置类
 *
 * @author xiezm
 */
public class DefaultObjectMapper extends ObjectMapper {

    public DefaultObjectMapper() {
        //忽略 前端js对象的属性 序列化成 java对象没有对应的get set 方法 映射错误
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
