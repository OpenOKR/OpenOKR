package org.openokr.enumerate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc:   用户角色枚举类
 * @author: cww
 * @DateTime: 2019/10/17 14:29
 */

public enum RoleEnum {

    /**
     * okr普通用户的角色id
     */
    COMMON_USER_ID_DEV("01","dev","004e22e742454e83b2e19db384a5dad0"),
    COMMON_USER_ID_LOCAL("01","local","004e22e742454e83b2e19db384a5dad0"),
    COMMON_USER_ID_PRD("01","prd","004e22e742454e83b2e19db384a5dad0"),
    COMMON_USER_ID_TEST("01","test","004e22e742454e83b2e19db384a5dad0"),
    /**
     * okr管理员用户的角色id
     */
    ADMIN_USER_ID_DEV("00","dev","1"),
    ADMIN_USER_ID_LOCAL("00","local","1"),
    ADMIN_USER_ID_PRD("00","prd","1"),
    ADMIN_USER_ID_TEST("00","test","1");

    /**
     * 用来查询对应 roleId 用的，用于接口的入参
     */
    private String id;
    private String roleId;
    /**
     * 当前运行环境
     */
    private String profile;

    /**
     * 角色筛选与筛选条件关联
     */
    private static Map<String, String> filter = new HashMap<>();
    static {
        // baseDn 需要根据实际配置文件替换
        filter.put("00","(&(|(objectclass=person))(|(|(memberof=CN=app-admin,CN=Users,baseDn))))");
        filter.put("01","(&(|(objectclass=person))(|(|(memberof=CN=yqb-okr-user,CN=Users,baseDn))))");
    }

    private RoleEnum(String id, String profile, String roleId) {
        this.id = id;
        this.profile = profile;
        this.roleId = roleId;
    }

    public static String getRoleId(String id, String profile) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.id.equals(id) && roleEnum.profile.equals(profile)) {
                return roleEnum.roleId;
            }
        }
        return null;
    }

    public static List<String> getRoleArrayByProfile(String profile) {
        List<String> roleList = new ArrayList<>();
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.profile.equals(profile)) {
                roleList.add(roleEnum.id);
            }
        }
        return roleList;
    }

    public static String getFilterByProfileAndRole(String role) {
        return filter.get(role);
    }
}
