package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;

public class RolePermissionVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 权限Id
     */
    private String permissionId;



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
     * 角色Id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * 角色Id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 权限Id
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * 权限Id
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

}