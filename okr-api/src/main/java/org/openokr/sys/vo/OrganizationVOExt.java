package org.openokr.sys.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzheng on 2018/12/21.
 */
public class OrganizationVOExt extends OrganizationVO {

    private String parentName;

    private List<UserVO> userVOList;

    private List<OrganizationVOExt> children = new ArrayList<>();

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<UserVO> getUserVOList() {
        return userVOList;
    }

    public void setUserVOList(List<UserVO> userVOList) {
        this.userVOList = userVOList;
    }

    public List<OrganizationVOExt> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationVOExt> children) {
        this.children = children;
    }

}