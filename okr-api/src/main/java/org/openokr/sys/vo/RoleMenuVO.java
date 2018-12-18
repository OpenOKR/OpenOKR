package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class RoleMenuVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 更新人
     */
    private String updateUserId;

    /**
     * 更新时间
     */
    private Date updateTs;

    /**
     * 树节点级次
     */
    private Short treeLevel;



    /**
     * 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 角色ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 角色ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 菜单ID
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * 菜单ID
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    /**
     * 创建人
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 创建时间
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建时间
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 更新人
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新人
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新时间
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 树节点级次
     */
    public Short getTreeLevel() {
        return treeLevel;
    }

    /**
     * 树节点级次
     */
    public void setTreeLevel(Short treeLevel) {
        this.treeLevel = treeLevel;
    }

}