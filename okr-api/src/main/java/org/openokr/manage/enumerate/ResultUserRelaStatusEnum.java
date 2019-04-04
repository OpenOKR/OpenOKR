package org.openokr.manage.enumerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzheng on 2019/2/3.
 */
public enum ResultUserRelaStatusEnum {

    /**
     * 0-待确认
     */
    STATUS_1("1", "待确认"),

    /**
     * 1-确认
     */
    STATUS_2("2", "确认"),

    /**
     * 2-拒绝
     */
    STATUS_3("3", "拒绝");

    private String name;

    private String code;

    ResultUserRelaStatusEnum(String code, String name) {
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
            for (ResultUserRelaStatusEnum enumerate : ResultUserRelaStatusEnum.values()) {
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
            for (ResultUserRelaStatusEnum enumerate : ResultUserRelaStatusEnum.values()) {
                map.put(enumerate.getCode(), enumerate.getName());
            }
        }
        return map;
    }

    public static ResultUserRelaStatusEnum getByCode(String code) {
        ResultUserRelaStatusEnum _enum = null;
        for (ResultUserRelaStatusEnum enumerate : ResultUserRelaStatusEnum.values()) {
            if (enumerate.getCode().equals(code)) {
                _enum = enumerate;
                break;
            }
        }
        return _enum;
    }

}
