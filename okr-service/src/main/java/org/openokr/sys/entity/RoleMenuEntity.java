package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_sys_role_menu")
public class RoleMenuEntity extends BaseEntity implements Serializable {
    /** 角色ID */
    private String roleId;

    /** 菜单ID */
    private String menuId;

    /** 创建人 */
    private String createUserId;

    /** 创建时间 */
    private Date createTs;

    /** 更新人 */
    private String updateUserId;

    /** 更新时间 */
    private Date updateTs;

    /** 树节点级次 */
    private Short treeLevel;

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     * @return roleId
     */
    @Column(name = "role_id")
    public String getRoleId() {
        return roleId;
    }

    /**
     * 角色ID
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 菜单ID
     * @return menuId
     */
    @Column(name = "menu_id")
    public String getMenuId() {
        return menuId;
    }

    /**
     * 菜单ID
     * @param menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * 创建人
     * @return createUserId
     */
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     * @param createUserId
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 创建时间
     * @return createTs
     */
    @Column(name = "create_ts")
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建时间
     * @param createTs
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 更新人
     * @return updateUserId
     */
    @Column(name = "update_user_id")
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新人
     * @param updateUserId
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 更新时间
     * @return updateTs
     */
    @Column(name = "update_ts")
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新时间
     * @param updateTs
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 树节点级次
     * @return treeLevel
     */
    @Column(name = "tree_level")
    public Short getTreeLevel() {
        return treeLevel;
    }

    /**
     * 树节点级次
     * @param treeLevel
     */
    public void setTreeLevel(Short treeLevel) {
        this.treeLevel = treeLevel;
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return RoleMenuEntityMapper.class;
    }
}