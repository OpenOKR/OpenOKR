package org.openokr.task.entity;

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
@Table(name = "t_okr_weekly")
public class WeeklyEntity extends BaseEntity implements Serializable {
    /** 填写人 */
    private String reportUserId;

    /** 任务ID */
    private String taskId;

    /** 关联的任务 */
    private String relTaskId;

    /** 填报开始周期 */
    private Date reportStartDate;

    /** 填报结束周期 */
    private Date reportEndDate;

    /** 耗费总工时 */
    private BigDecimal duration;

    /** 备注信息 */
    private String remark;

    /** 审批状态: 0 待审核 1 已确认 2 已驳回 */
    private String auditStatus;

    /** 驳回原因 */
    private String rejectReason;

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
     * 关联的任务
     * @return relTaskId
     */
    @Column(name = "rel_task_id")
    public String getRelTaskId() {
        return relTaskId;
    }

    /**
     * 关联的任务
     * @param relTaskId
     */
    public void setRelTaskId(String relTaskId) {
        this.relTaskId = relTaskId;
        addSettedField("relTaskId");
    }

    /**
     * 填报开始周期
     * @return reportStartDate
     */
    @Column(name = "report_start_date")
    public Date getReportStartDate() {
        return reportStartDate;
    }

    /**
     * 填报开始周期
     * @param reportStartDate
     */
    public void setReportStartDate(Date reportStartDate) {
        this.reportStartDate = reportStartDate;
        addSettedField("reportStartDate");
    }

    /**
     * 填报结束周期
     * @return reportEndDate
     */
    @Column(name = "report_end_date")
    public Date getReportEndDate() {
        return reportEndDate;
    }

    /**
     * 填报结束周期
     * @param reportEndDate
     */
    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
        addSettedField("reportEndDate");
    }

    /**
     * 耗费总工时
     * @return duration
     */
    @Column(name = "duration")
    public BigDecimal getDuration() {
        return duration;
    }

    /**
     * 耗费总工时
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
     * 审批状态: 0 待审核 1 已确认 2 已驳回
     * @return auditStatus
     */
    @Column(name = "audit_status")
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * 审批状态: 0 待审核 1 已确认 2 已驳回
     * @param auditStatus
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
        addSettedField("auditStatus");
    }

    /**
     * 驳回原因
     * @return rejectReason
     */
    @Column(name = "reject_reason")
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * 驳回原因
     * @param rejectReason
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
        addSettedField("rejectReason");
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
        return WeeklyEntityMapper.class;
    }
}