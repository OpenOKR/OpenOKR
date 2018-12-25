package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_manage_results")
public class ResultsEntity extends BaseEntity implements Serializable {
    /** 目标id */
    private String objectId;

    /** 名称 */
    private String name;

    /** 描述 */
    private String description;

    /** 评价单位（是否、百分比、数值） */
    private String metricUnit;

    /** 目标值 */
    private String targetValue;

    /** 起始值 */
    private String initialValue;

    /** 当前值 */
    private String currentValue;

    /** 负责人 */
    private String ownerId;

    /** 完成时间 */
    private Date endTs;

    /** 上一次的名称 */
    private String previousName;

    /** 执行状态 0-未开始，1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成 */
    private String status;

    /** 删除标识 0-否 1-是 */
    private String delFlag;

    /** 当前进度（百分比) */
    private BigDecimal progress;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    /** 更新时间 */
    private Date updateTs;

    /** 更新者 */
    private String updateUserId;

    /** 上一次的进度 */
    private BigDecimal preProgress;

    private static final long serialVersionUID = 1L;

    /**
     * 目标id
     * @return objectId
     */
    @Column(name = "object_id")
    public String getObjectId() {
        return objectId;
    }

    /**
     * 目标id
     * @param objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
        addSettedField("objectId");
    }

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
     * 描述
     * @return description
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
        addSettedField("description");
    }

    /**
     * 评价单位（是否、百分比、数值）
     * @return metricUnit
     */
    @Column(name = "metric_unit")
    public String getMetricUnit() {
        return metricUnit;
    }

    /**
     * 评价单位（是否、百分比、数值）
     * @param metricUnit
     */
    public void setMetricUnit(String metricUnit) {
        this.metricUnit = metricUnit;
        addSettedField("metricUnit");
    }

    /**
     * 目标值
     * @return targetValue
     */
    @Column(name = "target_value")
    public String getTargetValue() {
        return targetValue;
    }

    /**
     * 目标值
     * @param targetValue
     */
    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
        addSettedField("targetValue");
    }

    /**
     * 起始值
     * @return initialValue
     */
    @Column(name = "initial_value")
    public String getInitialValue() {
        return initialValue;
    }

    /**
     * 起始值
     * @param initialValue
     */
    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
        addSettedField("initialValue");
    }

    /**
     * 当前值
     * @return currentValue
     */
    @Column(name = "current_value")
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * 当前值
     * @param currentValue
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
        addSettedField("currentValue");
    }

    /**
     * 负责人
     * @return ownerId
     */
    @Column(name = "owner_id")
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 负责人
     * @param ownerId
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        addSettedField("ownerId");
    }

    /**
     * 完成时间
     * @return endTs
     */
    @Column(name = "end_ts")
    public Date getEndTs() {
        return endTs;
    }

    /**
     * 完成时间
     * @param endTs
     */
    public void setEndTs(Date endTs) {
        this.endTs = endTs;
        addSettedField("endTs");
    }

    /**
     * 上一次的名称
     * @return previousName
     */
    @Column(name = "previous_name")
    public String getPreviousName() {
        return previousName;
    }

    /**
     * 上一次的名称
     * @param previousName
     */
    public void setPreviousName(String previousName) {
        this.previousName = previousName;
        addSettedField("previousName");
    }

    /**
     * 执行状态 0-未开始，1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     * @return status
     */
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    /**
     * 执行状态 0-未开始，1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
        addSettedField("status");
    }

    /**
     * 删除标识 0-否 1-是
     * @return delFlag
     */
    @Column(name = "del_flag")
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标识 0-否 1-是
     * @param delFlag
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
        addSettedField("delFlag");
    }

    /**
     * 当前进度（百分比)
     * @return progress
     */
    @Column(name = "progress")
    public BigDecimal getProgress() {
        return progress;
    }

    /**
     * 当前进度（百分比)
     * @param progress
     */
    public void setProgress(BigDecimal progress) {
        this.progress = progress;
        addSettedField("progress");
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
     * 上一次的进度
     * @return preProgress
     */
    @Column(name = "pre_progress")
    public BigDecimal getPreProgress() {
        return preProgress;
    }

    /**
     * 上一次的进度
     * @param preProgress
     */
    public void setPreProgress(BigDecimal preProgress) {
        this.preProgress = preProgress;
        addSettedField("preProgress");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return ResultsEntityMapper.class;
    }
}