package org.openokr.task.entity;

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
@Table(name = "t_okr_customer")
public class CustomerEntity extends BaseEntity implements Serializable {
    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

    /** 备注 */
    private String remark;

    /** 创建者 */
    private String createUserId;

    /** 创建时间 */
    private Date createTs;

    /** 更新者 */
    private String updateUserId;

    /** 更新时间 */
    private Date updateTs;

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
     * 状态
     * @return status
     */
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
        addSettedField("status");
    }

    /**
     * 备注
     * @return remark
     */
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
        addSettedField("remark");
    }

    /**
     * 创建者
     * @return createUserId
     */
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建者
     * @param createUserId
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
        addSettedField("createUserId");
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
        addSettedField("createTs");
    }

    /**
     * 更新者
     * @return updateUserId
     */
    @Column(name = "update_user_id")
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新者
     * @param updateUserId
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        addSettedField("updateUserId");
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
        addSettedField("updateTs");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return CustomerEntityMapper.class;
    }
}