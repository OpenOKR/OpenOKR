package org.openokr.enumerate;

/**
 * @Desc:   组织结构枚举类 各环境的 园区宝研发中心 组织id
 * @author: cww
 * @DateTime: 2019/10/17 14:45
 */

public enum OrgEnum {
    /**
     *   ldap           okr
     *  研发中心  ---  园区宝研发中心
     */

    R_D_ORG_ID_DEV("01","dev","16100416","研发"),
    R_D_ORG_ID_LOCAL("01","local","16100416","研发"),
    R_D_ORG_ID_PRD("01","prd","16100416","研发"),
    R_D_ORG_ID_TEST("01","test","16100416","研发"),

    /**
     *   ldap           okr
     * 产品中心  -- 产品规划部
     */
    PM_ORG_ID_DEV("02","dev","16100419","产品"),
    PM_ORG_ID_LOCAL("02","local","16100419","产品"),
    PM_ORG_ID_TEST("02","prd","16100419","产品"),
    PM_ORG_ID_PRD("02","test","16100419","产品"),

    /**
     *   ldap           okr
     * 销售中心  -- 园区宝销售中心
     */
    MARKET_ORG_ID_DEV("03","dev","16100418","销售"),
    MARKET_ORG_ID_LOCAL("03","local","16100418","销售"),
    MARKET_ORG_ID_TEST("03","prd","16100418","销售"),
    MARKET_ORG_ID_PRD("03","test","16100418","销售"),

    ;

    /**
     * 用来查询对应 orgId 用的，用于接口的入参
     */
    private String id;
    /**
     * 当前运行环境
     */
    private String profile;
    /**
     * 组织id
     */
    private String orgId;
    /**
     * 部门模糊匹配简称 用于匹配ldap部门
     */
    private String desc;

    OrgEnum(String id, String profile, String orgId, String desc) {
        this.id = id;
        this.profile = profile;
        this.orgId = orgId;
        this.desc = desc;
    }

    public static String getOrgId(String id, String profile) {
        for (OrgEnum orgEnum : OrgEnum.values()) {
            if (orgEnum.id.equals(id) && orgEnum.profile.equals(profile)) {
                return orgEnum.orgId;
            }
        }
        return null;
    }

    /**
     * 根据环境参数与部门简称找出对应的 okr 中的部门id
     * @param profile
     * @param desc
     * @return
     */
    public static String getOrgIdByDesc(String profile, String desc) {
        for (OrgEnum orgEnum : OrgEnum.values()) {
            if (orgEnum.profile.equals(profile) && desc.contains(orgEnum.desc)) {
                return orgEnum.orgId;
            }
        }
        // 匹配不到，则默认先设置为研发部门
        return R_D_ORG_ID_PRD.orgId;
    }
}
