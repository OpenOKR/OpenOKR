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
@Table(name = "t_okr_task")
public class TaskEntity extends BaseEntity implements Serializable {
    /** 任务代码 */
    private String taskCode;

    /** 任务名称 */
    private String taskName;

    /** 项目状态：0 临时项目 1 正式项目 2 失效项目 */
    private String taskStatus;

    /** 是否删除: 0 正常 1 删除 */
    private String isDeleted;

    /** 任务开始时间 */
    private Date taskStartTime;

    /** 任务结束时间 */
    private Date taskEndTime;

    /** JIRA标签 */
    private String jiraLabel;

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
     * 任务代码
     * @return taskCode
     */
    @Column(name = "task_code")
    public String getTaskCode() {
        return taskCode;
    }

    /**
     * 任务代码
     * @param taskCode
     */
    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
        addSettedField("taskCode");
    }

    /**
     * 任务名称
     * @return taskName
     */
    @Column(name = "task_name")
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务名称
     * @param taskName
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
        addSettedField("taskName");
    }

    /**
     * 项目状态：0 临时项目 1 正式项目 2 失效项目
     * @return taskStatus
     */
    @Column(name = "task_status")
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * 项目状态：0 临时项目 1 正式项目 2 失效项目
     * @param taskStatus
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
        addSettedField("taskStatus");
    }

    /**
     * 是否删除: 0 正常 1 删除
     * @return isDeleted
     */
    @Column(name = "is_deleted")
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除: 0 正常 1 删除
     * @param isDeleted
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
        addSettedField("isDeleted");
    }

    /**
     * 任务开始时间
     * @return taskStartTime
     */
    @Column(name = "task_start_time")
    public Date getTaskStartTime() {
        return taskStartTime;
    }

    /**
     * 任务开始时间
     * @param taskStartTime
     */
    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
        addSettedField("taskStartTime");
    }

    /**
     * 任务结束时间
     * @return taskEndTime
     */
    @Column(name = "task_end_time")
    public Date getTaskEndTime() {
        return taskEndTime;
    }

    /**
     * 任务结束时间
     * @param taskEndTime
     */
    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
        addSettedField("taskEndTime");
    }

    /**
     * JIRA标签
     * @return jiraLabel
     */
    @Column(name = "jira_label")
    public String getJiraLabel() {
        return jiraLabel;
    }

    /**
     * JIRA标签
     * @param jiraLabel
     */
    public void setJiraLabel(String jiraLabel) {
        this.jiraLabel = jiraLabel;
        addSettedField("jiraLabel");
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
        return TaskEntityMapper.class;
    }
}