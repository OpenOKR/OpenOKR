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
@Table(name = "t_okr_manage_checkins")
public class CheckinsEntity extends BaseEntity implements Serializable {
    /** 结果id */
    private String resultId;

    /** 当前值 */
    private String currentValue;

    /** 执行状态 1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成 */
    private String status;

    /** 描述 */
    private String description;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    /** 评价单位（是否、百分比、数值） */
    private String metricUnit;

    private static final long serialVersionUID = 1L;

    /**
     * 结果id
     * @return resultId
     */
    @Column(name = "result_id")
    public String getResultId() {
        return resultId;
    }

    /**
     * 结果id
     * @param resultId
     */
    public void setResultId(String resultId) {
        this.resultId = resultId;
        addSettedField("resultId");
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
     * 执行状态 1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     * @return status
     */
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    /**
     * 执行状态 1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
        addSettedField("status");
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
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return CheckinsEntityMapper.class;
    }
}