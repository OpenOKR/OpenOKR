package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

public class ResultsVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 目标id
     */
    private String objectId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 评价单位（是否、百分比、数值）
     */
    private String metricUnit;

    /**
     * 目标值
     */
    private String targetValue;

    /**
     * 起始值
     */
    private String initialValue;

    /**
     * 当前值
     */
    private String currentValue;

    /**
     * 负责人
     */
    private String ownerId;

    /**
     * 完成时间
     */
    private Date endTs;

    /**
     * 上一次的名称
     */
    private String previousName;

    /**
     * 执行状态 0-未开始，1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     */
    private String status;

    /**
     * 删除标识 0-否 1-是
     */
    private String delFlag;

    /**
     * 当前进度（百分比)
     */
    private BigDecimal progress;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private Date updateTs;

    /**
     * 更新者
     */
    private String updateUserId;



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
     * 目标id
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * 目标id
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 评价单位（是否、百分比、数值）
     */
    public String getMetricUnit() {
        return metricUnit;
    }

    /**
     * 评价单位（是否、百分比、数值）
     */
    public void setMetricUnit(String metricUnit) {
        this.metricUnit = metricUnit;
    }

    /**
     * 目标值
     */
    public String getTargetValue() {
        return targetValue;
    }

    /**
     * 目标值
     */
    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    /**
     * 起始值
     */
    public String getInitialValue() {
        return initialValue;
    }

    /**
     * 起始值
     */
    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }

    /**
     * 当前值
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * 当前值
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * 负责人
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 负责人
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 完成时间
     */
    public Date getEndTs() {
        return endTs;
    }

    /**
     * 完成时间
     */
    public void setEndTs(Date endTs) {
        this.endTs = endTs;
    }

    /**
     * 上一次的名称
     */
    public String getPreviousName() {
        return previousName;
    }

    /**
     * 上一次的名称
     */
    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }

    /**
     * 执行状态 0-未开始，1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     */
    public String getStatus() {
        return status;
    }

    /**
     * 执行状态 0-未开始，1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 删除标识 0-否 1-是
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标识 0-否 1-是
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 当前进度（百分比)
     */
    public BigDecimal getProgress() {
        return progress;
    }

    /**
     * 当前进度（百分比)
     */
    public void setProgress(BigDecimal progress) {
        this.progress = progress;
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
     * 创建者
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建者
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
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
     * 更新者
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新者
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

}