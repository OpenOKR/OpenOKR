package org.openokr.sys.vo;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public class RoleVOExt extends RoleVO {

    private UserVOExt userVOExt;

    private List<String> permissionList;

    public RoleVOExt() {
        super();
    }

    public RoleVOExt(UserVOExt userVOExt) {
        this();
        this.userVOExt = userVOExt;
    }

    public UserVOExt getUserVOExt() {
        return userVOExt;
    }

    public void setUserVOExt(UserVOExt userVOExt) {
        this.userVOExt = userVOExt;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }
}