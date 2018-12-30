package org.openokr.sys.vo;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public class UserVOExt extends UserVO {

    private String organizationName;

    private String organizationCode;

    private String roleId;
    private String roleName;

    private List<RoleVOExt> roleVOExtList;
    private List<MenuVOExt> menuVOExtList;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<RoleVOExt> getRoleVOExtList() {
        return roleVOExtList;
    }

    public void setRoleVOExtList(List<RoleVOExt> roleVOExtList) {
        this.roleVOExtList = roleVOExtList;
    }

    public List<MenuVOExt> getMenuVOExtList() {
        return menuVOExtList;
    }

    public void setMenuVOExtList(List<MenuVOExt> menuVOExtList) {
        this.menuVOExtList = menuVOExtList;
    }
}