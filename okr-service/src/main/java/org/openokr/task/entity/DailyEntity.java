package org.openokr.task.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 */
@Entity
@Table(name = "t_okr_daily")
public class DailyEntity extends BaseEntity implements Serializable {
    /** 填写人 */
    private String reportUserId;

    /** 任务ID */
    private String taskId;

    /** 填报日期 */
    private Date reportDay;

    /** 报工时长 */
    private BigDecimal duration;

    /** 备注信息 */
    private String remark;

    /** 创建者 */
    private String createUserId;

    /** 创建时间 */
    private Date createTs;

    /** 更新者 */
    private String updateUserId;

    /** 更新时间 */
    private Date updateTs;

    /** 日报审批状态：
null/00  待审核
01         已确认
02         已驳回 */
    private String auditStatus;

    private static final long serialVersionUID = 1L;

    /**
     * 填写人
     * @return reportUserId
     */
    @Column(name = "report_user_id")
    public String getReportUserId() {
        return reportUserId;
    }

    /**
     * 填写人
     * @param reportUserId
     */
    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
        addSettedField("reportUserId");
    }

    /**
     * 任务ID
     * @return taskId
     */
    @Column(name = "task_id")
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     * @param taskId
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
        addSettedField("taskId");
    }

    /**
     * 填报日期
     * @return reportDay
     */
    @Column(name = "report_day")
    public Date getReportDay() {
        return reportDay;
    }

    /**
     * 填报日期
     * @param reportDay
     */
    public void setReportDay(Date reportDay) {
        this.reportDay = reportDay;
        addSettedField("reportDay");
    }

    /**
     * 报工时长
     * @return duration
     */
    @Column(name = "duration")
    public BigDecimal getDuration() {
        return duration;
    }

    /**
     * 报工时长
     * @param duration
     */
    public void setDuration(BigDecimal duration) {
        this.duration = duration;
        addSettedField("duration");
    }

    /**
     * 备注信息
     * @return remark
     */
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    /**
     * 备注信息
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
     * 日报审批状态：
null/00  待审核
01         已确认
02         已驳回
     * @return auditStatus
     */
    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * 日报审批状态：
null/00  待审核
01         已确认
02         已驳回
     * @param auditStatus
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
        addSettedField("auditStatus");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return DailyEntityMapper.class;
    }
}