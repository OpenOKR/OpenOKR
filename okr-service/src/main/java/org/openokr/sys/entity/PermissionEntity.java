package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_sys_permission")
public class PermissionEntity extends BaseEntity implements Serializable {
    /** 名称 */
    private String name;

    /** 菜单Id */
    private String menuId;

    /** 描述 */
    private String description;

    /**  */
    private String code;

    private static final long serialVersionUID = 1L;

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
     * 菜单Id
     * @return menuId
     */
    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    /**
     * 菜单Id
     * @param menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
        addSettedField("menuId");
    }

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
     * 
     * @return code
     */
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
        addSettedField("code");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return PermissionEntityMapper.class;
    }
}