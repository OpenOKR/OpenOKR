package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class UserRoleVO extends BaseVO implements Serializable {

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
     * 用户Id
     */
    private String userId;

    /**
     * 
     */
    private String createUserId;

    /**
     * 
     */
    private Date createTs;

    /**
     * 
     */
    private String updateUserId;

    /**
     * 
     */
    private Date updateTs;



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
     * 用户Id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户Id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

}