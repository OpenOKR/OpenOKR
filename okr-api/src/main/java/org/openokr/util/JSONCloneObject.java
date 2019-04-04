package org.openokr.util;


import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @Description :
 * @author: cww
 * @DateTime: 2019/3/22 11:27
 */

public class JSONCloneObject {
    /**
     * 深度复制对象
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T cloneObject(Object source, Class target) {
        String json = JSON.toJSONString(source);
        return (T) JSON.parseObject(json, target);
    }

    /**
     * 深度复制列表
     * @param source
     * @param targetClass
     * @return
     */
    public static <T, E> List<E> cloneListObject(Object source, Class targetClass) {
        String json = JSON.toJSONString(source);
        return JSON.parseArray(json, targetClass);
    }
}
