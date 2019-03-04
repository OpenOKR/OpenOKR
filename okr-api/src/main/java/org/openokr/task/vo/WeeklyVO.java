package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WeeklyVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 填写人
     */
    private String reportUserId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 关联的任务
     */
    private String relTaskId;

    /**
     * 填报周期
     */
    private String period;

    /**
     * 耗费总工时
     */
    private BigDecimal duration;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 审批状态: 0 待审核 1 已确认 2 已驳回
     */
    private String auditStatus;

    /**
     * 驳回原因
     */
    private String rejectReason;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 更新者
     */
    private String updateUserId;

    /**
     * 更新时间
     */
    private Date updateTs;



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
     * 填写人
     */
    public String getReportUserId() {
        return reportUserId;
    }

    /**
     * 填写人
     */
    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

    /**
     * 任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 任务ID
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * 关联的任务
     */
    public String getRelTaskId() {
        return relTaskId;
    }

    /**
     * 关联的任务
     */
    public void setRelTaskId(String relTaskId) {
        this.relTaskId = relTaskId;
    }

    /**
     * 填报周期
     */
    public String getPeriod() {
        return period;
    }

    /**
     * 填报周期
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 耗费总工时
     */
    public BigDecimal getDuration() {
        return duration;
    }

    /**
     * 耗费总工时
     */
    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    /**
     * 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 审批状态: 0 待审核 1 已确认 2 已驳回
     */
    public String getAuditStatus() {
        return auditStatus;
    }

    /**
     * 审批状态: 0 待审核 1 已确认 2 已驳回
     */
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 驳回原因
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * 驳回原因
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

}