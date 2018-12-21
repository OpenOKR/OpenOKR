package org.openokr.sys.vo;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public class UserVOExt extends UserVO {

    private List<RoleVOExt> roleVOExtList;
    private List<MenuVOExt> menuVOExtList;

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