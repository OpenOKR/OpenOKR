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
@Table(name = "t_okr_sys_user")
public class UserEntity extends BaseEntity implements Serializable {
    /** 创建时间 */
    private Date createTime;

    /** 密码 */
    private String password;

    /** 密码加密 盐 */
    private String salt;

    /** 是否激活 1-激活，0-失效 */
    private Boolean active;

    /** 用户名 */
    private String userName;

    /** 邮件 */
    private String email;

    /** 真是姓名 */
    private String realName;

    /** 手机号 */
    private String phone;

    /** 机构Id */
    private String organizationId;

    /** 创建人id */
    private String createUserId;

    /** 创建日期 */
    private Date createTs;

    /** 更新人id */
    private String updateUserId;

    /** 更新日期 */
    private Date updateTs;

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     * @return createTime
     */
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 密码
     * @return password
     */
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 密码加密 盐
     * @return salt
     */
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    /**
     * 密码加密 盐
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 是否激活 1-激活，0-失效
     * @return active
     */
    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    /**
     * 是否激活 1-激活，0-失效
     * @param active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * 用户名
     * @return userName
     */
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 邮件
     * @return email
     */
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    /**
     * 邮件
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 真是姓名
     * @return realName
     */
    @Column(name = "real_name")
    public String getRealName() {
        return realName;
    }

    /**
     * 真是姓名
     * @param realName
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 手机号
     * @return phone
     */
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    /**
     * 手机号
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 机构Id
     * @return organizationId
     */
    @Column(name = "organization_id")
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * 机构Id
     * @param organizationId
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 创建人id
     * @return createUserId
     */
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人id
     * @param createUserId
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 创建日期
     * @return createTs
     */
    @Column(name = "create_ts")
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建日期
     * @param createTs
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 更新人id
     * @return updateUserId
     */
    @Column(name = "update_user_id")
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新人id
     * @param updateUserId
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 更新日期
     * @return updateTs
     */
    @Column(name = "update_ts")
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新日期
     * @param updateTs
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return UserEntityMapper.class;
    }
}