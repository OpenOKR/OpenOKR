package org.openokr.manage.entity;

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
@Table(name = "t_okr_time_sessions")
public class TimeSessionsEntity extends BaseEntity implements Serializable {
    /** 名称 */
    private String name;

    /** 开始日期 */
    private Date startDate;

    /** 结束日期 */
    private Date endDate;

    /** 是否激活 0-否， 1-是 */
    private String isActivate;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    /** 更新时间 */
    private Date updateTs;

    /** 更新者 */
    private String updateUserId;

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
    }

    /**
     * 开始日期
     * @return startDate
     */
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 开始日期
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 结束日期
     * @return endDate
     */
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 结束日期
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 是否激活 0-否， 1-是
     * @return isActivate
     */
    @Column(name = "is_activate")
    public String getIsActivate() {
        return isActivate;
    }

    /**
     * 是否激活 0-否， 1-是
     * @param isActivate
     */
    public void setIsActivate(String isActivate) {
        this.isActivate = isActivate;
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
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return TimeSessionsEntityMapper.class;
    }
}