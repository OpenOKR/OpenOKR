package org.openokr.manage.enumerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * message type 类型枚举
 * Created by zhengzheng on 2019/1/4.
 */
public enum  MessageTypeEnum {

    /**
     * 1-一般文本
     */
    TYPE_1("1", "一般文本"),

    /**
     * 2-目标审核
     */
    TYPE_2("2", "目标审核"),

    /**
     * 3-协同审核
     */
    TYPE_3("3", "协同审核"),

    /**
     * 4-目标跳转
     */
    TYPE_4("4", "目标跳转"),

    DEFAULT("", "");

    private String name;

    private String code;

    MessageTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private static List<Map<String, Object>> list = new ArrayList<>();

    private static Map<String, Object> map = new HashMap<>();

    /**
     * 转换成List<Map>
     * JOSN类似：[{code:1,name:"名称1"},{code:2,name:"名称2"},{code:3,name:"名称3"}...]
     */
    public static List<Map<String, Object>> toList() {
        if (list.isEmpty()) {
            for (MessageTypeEnum enumerate : MessageTypeEnum.values()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", enumerate.getName());
                map.put("code", enumerate.getCode());
                //
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 转换成Map
     * JOSN类似：{"1":"名称1","2":"名称2","3":"名称3"}
     */
    public static Map<String, Object> toMap() {
        if (map.isEmpty()) {
            for (MessageMarkEnum enumerate : MessageMarkEnum.values()) {
                map.put(enumerate.getCode(), enumerate.getName());
            }
        }
        return map;
    }

    public static MessageTypeEnum getByCode(String code) {
        MessageTypeEnum _enum = DEFAULT;
        for (MessageTypeEnum enumerate : MessageTypeEnum.values()) {
            if (enumerate.getCode().equals(code)) {
                _enum = enumerate;
                break;
            }
        }
        return _enum;
    }

}