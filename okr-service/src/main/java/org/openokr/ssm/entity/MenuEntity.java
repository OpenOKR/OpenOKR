package org.openokr.ssm.entity;

import com.jh.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_pfm_ssm_menu")
public class MenuEntity extends BaseEntity implements Serializable {
    /** 描述 */
    private String description;

    /** 名称 */
    private String name;

    /** 优先级 */
    private Integer priority;

    /** 链接 */
    private String url;

    /** 父Id */
    private String parentId;

    /** 权限前缀码 */
    private String permissionPrefixCode;

    private static final long serialVersionUID = 1L;

    /**
     * 描述
     * @return description
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
        addSettedField("description");
    }

    /**
     * 名称
     * @return name
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        addSettedField("name");
    }

    /**
     * 优先级
     * @return priority
     */
    @Column(name = "priority")
    public Integer getPriority() {
        return priority;
    }

    /**
     * 优先级
     * @param priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
        addSettedField("priority");
    }

    /**
     * 链接
     * @return url
     */
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    /**
     * 链接
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
        addSettedField("url");
    }

    /**
     * 父Id
     * @return parentId
     */
    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    /**
     * 父Id
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
        addSettedField("parentId");
    }

    /**
     * 权限前缀码
     * @return permissionPrefixCode
     */
    @Column(name = "permission_prefix_code")
    public String getPermissionPrefixCode() {
        return permissionPrefixCode;
    }

    /**
     * 权限前缀码
     * @param permissionPrefixCode
     */
    public void setPermissionPrefixCode(String permissionPrefixCode) {
        this.permissionPrefixCode = permissionPrefixCode;
        addSettedField("permissionPrefixCode");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return MenuEntityMapper.class;
    }
}