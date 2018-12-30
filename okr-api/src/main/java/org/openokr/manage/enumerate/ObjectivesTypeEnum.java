package org.openokr.manage.enumerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Objectives 类型枚举
 * Created by zhengzheng on 2018/12/24.
 */
public enum ObjectivesTypeEnum {

    /**
     * 1-个人
     */
    TYPE_1("1", "个人"),

    /**
     * 2-团队
     */
    TYPE_2("2", "团队"),

    /**
     * 3-公司
     */
    TYPE_3("3", "公司"),

    DEFAULT("", "");

    private String name;

    private String code;

    ObjectivesTypeEnum(String code, String name) {
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
            for (ObjectivesTypeEnum enumerate : ObjectivesTypeEnum.values()) {
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
            for (ObjectivesTypeEnum enumerate : ObjectivesTypeEnum.values()) {
                map.put(enumerate.getCode(), enumerate.getName());
            }
        }
        return map;
    }

    public static ObjectivesTypeEnum getByCode(String code) {
        ObjectivesTypeEnum _enum = DEFAULT;
        for (ObjectivesTypeEnum enumerate : ObjectivesTypeEnum.values()) {
            if (enumerate.getCode().equals(code)) {
                _enum = enumerate;
                break;
            }
        }
        return _enum;
    }
}