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
@Table(name = "t_okr_sys_role_permission")
public class RolePermissionEntity extends BaseEntity implements Serializable {
    /** 角色Id */
    private String roleId;

    /** 权限Id */
    private String permissionId;

    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     * @return roleId
     */
    @Column(name = "role_id")
    public String getRoleId() {
        return roleId;
    }

    /**
     * 角色Id
     * @param roleId
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 权限Id
     * @return permissionId
     */
    @Column(name = "permission_id")
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * 权限Id
     * @param permissionId
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return RolePermissionEntityMapper.class;
    }
}