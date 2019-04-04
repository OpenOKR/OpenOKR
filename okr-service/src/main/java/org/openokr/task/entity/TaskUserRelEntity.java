package org.openokr.task.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_task_user_rel")
public class TaskUserRelEntity extends BaseEntity implements Serializable {
    /** 任务ID */
    private String taskId;

    /** 用户ID */
    private String userId;

    private static final long serialVersionUID = 1L;

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
     * 用户ID
     * @return userId
     */
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
        addSettedField("userId");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return TaskUserRelEntityMapper.class;
    }
}