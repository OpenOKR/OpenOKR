package org.openokr.enumerate;

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
}
