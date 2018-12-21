package org.openokr.sys.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzheng on 2018/12/18.
 */
public class MenuVOExt extends MenuVO {

    private String parentName;

    private List<PermissionVOExt> permissionVOExtList = new ArrayList<>();

    private List<MenuVOExt> children = new ArrayList<>();

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<PermissionVOExt> getPermissionVOExtList() {
        return permissionVOExtList;
    }

    public void setPermissionVOExtList(List<PermissionVOExt> permissionVOExtList) {
        this.permissionVOExtList = permissionVOExtList;
    }

    public List<MenuVOExt> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVOExt> children) {
        this.children = children;
    }
}