package org.openokr.manage.enumerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * message mark 类型枚举
 * Created by zhengzheng on 2018/12/25.
 */
public enum MessageMarkEnum {

    /**
     * 1-警告
     */
    MARK_1("1", "警告", "iconfont icon-waring text-primary"),

    /**
     * 2-通过
     */
    MARK_2("2", "通过", "iconfont icon-succ text-success"),

    /**
     * 3-拒绝
     */
    MARK_3("3", "拒绝", "iconfont icon-waring text-warning"),

    /**
     * 4-提醒
     */
    MARK_4("4", "提醒", "iconfont icon-tip text-danger"),

    DEFAULT("", "", "");

    private String name;

    private String code;

    private String cssClass;

    MessageMarkEnum(String code, String name, String cssClass) {
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
            for (MessageMarkEnum enumerate : MessageMarkEnum.values()) {
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
            for (MessageMarkEnum enumerate : MessageMarkEnum.values()) {
                map.put(enumerate.getCode(), enumerate.getName());
            }
        }
        return map;
    }

    public static MessageMarkEnum getByCode(String code) {
        MessageMarkEnum _enum = DEFAULT;
        for (MessageMarkEnum enumerate : MessageMarkEnum.values()) {
            if (enumerate.getCode().equals(code)) {
                _enum = enumerate;
                break;
            }
        }
        return _enum;
    }
}