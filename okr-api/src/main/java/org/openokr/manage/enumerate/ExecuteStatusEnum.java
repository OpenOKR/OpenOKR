package org.openokr.manage.enumerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行状态枚举类
 */
public enum ExecuteStatusEnum {

    /**
     * 0-未开始
     */
    STATUS_0("0", "未开始", "text-muted"),

    /**
     * 1-正常执行
     */
    STATUS_1("1", "正常执行", "text-primary"),

    /**
     * 2-执行有风险
     */
    STATUS_2("2", "执行有风险", "text-danger"),

    /**
     * 3-暂停执行
     */
    STATUS_3("3", "暂停执行", "text-danger"),

    /**
     * 4-提前终止
     */
    STATUS_4("4", "提前终止", "text-warning"),

    /**
     * 5-完成
     */
    STATUS_5("5", "完成", "text-success");

    private String name;

    private String code;

    private String cssClass;

    ExecuteStatusEnum(String code, String name, String cssClass) {
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
            for (ExecuteStatusEnum enumerate : ExecuteStatusEnum.values()) {
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
            for (ExecuteStatusEnum enumerate : ExecuteStatusEnum.values()) {
                map.put(enumerate.getCode(), enumerate.getName());
            }
        }
        return map;
    }

}
