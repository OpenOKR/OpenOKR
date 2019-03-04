package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class TaskVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 任务代码
     */
    private String taskCode;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 项目状态：0 临时项目 1 正式项目 2 失效项目
     */
    private String taskStatus;

    /**
     * 任务描述
     */
    private String taskRemark;

    /**
     * 是否删除: 0 正常 1 删除
     */
    private String isDeleted;

    /**
     * 任务开始时间
     */
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    private Date taskEndTime;

    /**
     * JIRA标签
     */
    private String jiraLabel;

    /**
     * 确认人
     */
    private String confirmUserId;

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
     * 任务代码
     */
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 任务代码
     */
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    /**
     * 任务名称
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务名称
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 项目状态：0 临时项目 1 正式项目 2 失效项目
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * 项目状态：0 临时项目 1 正式项目 2 失效项目
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 任务描述
     */
    public String getTaskRemark() {
        return taskRemark;
    }

    /**
     * 任务描述
     */
    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    /**
     * 是否删除: 0 正常 1 删除
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除: 0 正常 1 删除
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 任务开始时间
     */
    public Date getTaskStartTime() {
        return taskStartTime;
    }

    /**
     * 任务开始时间
     */
    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    /**
     * 任务结束时间
     */
    public Date getTaskEndTime() {
        return taskEndTime;
    }

    /**
     * 任务结束时间
     */
    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    /**
     * JIRA标签
     */
    public String getJiraLabel() {
        return jiraLabel;
    }

    /**
     * JIRA标签
     */
    public void setJiraLabel(String jiraLabel) {
        this.jiraLabel = jiraLabel;
    }

    /**
     * 确认人
     */
    public String getConfirmUserId() {
        return confirmUserId;
    }

    /**
     * 确认人
     */
    public void setConfirmUserId(String confirmUserId) {
        this.confirmUserId = confirmUserId;
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