package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class UserVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码加密 盐
     */
    private String salt;

    /**
     * 是否激活 1-激活，0-失效
     */
    private Boolean active;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 邮件
     */
    private String email;

    /**
     * 真是姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 机构Id
     */
    private String organizationId;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建日期
     */
    private Date createTs;

    /**
     * 更新人id
     */
    private String updateUserId;

    /**
     * 更新日期
     */
    private Date updateTs;

    /**
     * 个人头像URL地址
     */
    private String photoUrl;



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
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 密码加密 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 密码加密 盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 是否激活 1-激活，0-失效
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * 是否激活 1-激活，0-失效
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 真是姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 真是姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 机构Id
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * 机构Id
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 创建人id
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人id
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 创建日期
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建日期
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 更新人id
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新人id
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 更新日期
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新日期
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 个人头像URL地址
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 个人头像URL地址
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}