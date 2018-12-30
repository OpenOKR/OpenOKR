package org.openokr.manage.enumerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * objectives status 枚举
 * Created by zhengzheng on 2018/12/25.
 */
public enum ObjectivesStatusEnum {

    /**
     * 1-未提交
     */
    STATUS_1("1", "未提交", "gary"),

    /**
     * 2-待确认
     */
    STATUS_2("2", "待确认", "org"),

    /**
     * 3-已确认
     */
    STATUS_3("3", "已确认", "green"),

    /**
     * 4-被驳回
     */
    STATUS_4("4", "被驳回", "red"),

    DEFAULT("", "", "");

    private String name;

    private String code;

    private String cssClass;

    ObjectivesStatusEnum(String code, String name, String cssClass) {
        this.code = code;
        this.name = name;
        this.cssClass = cssClass;
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

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    private static List<Map<String, Object>> list = new ArrayList<>();

    private static Map<String, Object> map = new HashMap<>();

    /**
     * 转换成List<Map>
     * JOSN类似：[{code:1,name:"名称1"},{code:2,name:"名称2"},{code:3,name:"名称3"}...]
     */
    public static List<Map<String, Object>> toList() {
        if (list.isEmpty()) {
            for (ObjectivesStatusEnum enumerate : ObjectivesStatusEnum.values()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", enumerate.getName());
                map.put("code", enumerate.getCode());
                map.put("cssClass", enumerate.getCssClass());
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
            for (ObjectivesStatusEnum enumerate : ObjectivesStatusEnum.values()) {
                map.put(enumerate.getCode(), enumerate.getName());
            }
        }
        return map;
    }

    public static ObjectivesStatusEnum getByCode(String code) {
        ObjectivesStatusEnum _enum = DEFAULT;
        for (ObjectivesStatusEnum enumerate : ObjectivesStatusEnum.values()) {
            if (enumerate.getCode().equals(code)) {
                _enum = enumerate;
                break;
            }
        }
        return _enum;
    }
}