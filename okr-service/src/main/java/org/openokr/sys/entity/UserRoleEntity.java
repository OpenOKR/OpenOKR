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
@Table(name = "t_okr_sys_user_role")
public class UserRoleEntity extends BaseEntity implements Serializable {
    /** 角色Id */
    private String roleId;

    /** 用户Id */
    private String userId;

    /**  */
    private String createUserId;

    /**  */
    private Date createTs;

    /**  */
    private String updateUserId;

    /**  */
    private Date updateTs;

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
        addSettedField("roleId");
    }

    /**
     * 用户Id
     * @return userId
     */
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * 用户Id
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
        addSettedField("userId");
    }

    /**
     * 
     * @return createUserId
     */
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 
     * @param createUserId
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
        addSettedField("createUserId");
    }

    /**
     * 
     * @return createTs
     */
    @Column(name = "create_ts")
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 
     * @param createTs
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
        addSettedField("createTs");
    }

    /**
     * 
     * @return updateUserId
     */
    @Column(name = "update_user_id")
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 
     * @param updateUserId
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        addSettedField("updateUserId");
    }

    /**
     * 
     * @return updateTs
     */
    @Column(name = "update_ts")
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 
     * @param updateTs
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
        addSettedField("updateTs");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return UserRoleEntityMapper.class;
    }
}