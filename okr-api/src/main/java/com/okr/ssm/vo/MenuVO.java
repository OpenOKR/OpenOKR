package com.okr.ssm.vo;


import com.jh.framework.common.vo.BaseVO;

import java.io.Serializable;

public class MenuVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 描述
     */
    private String description;

    /**
     * 名称
     */
    private String name;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 链接
     */
    private String url;

    /**
     * 父Id
     */
    private String parentId;

    /**
     * 权限前缀码
     */
    private String permissionPrefixCode;



    /**
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 优先级
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 优先级
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 父Id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 父Id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 权限前缀码
     */
    public String getPermissionPrefixCode() {
        return permissionPrefixCode;
    }

    /**
     * 权限前缀码
     */
    public void setPermissionPrefixCode(String permissionPrefixCode) {
        this.permissionPrefixCode = permissionPrefixCode;
    }

}