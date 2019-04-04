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
@Table(name = "t_okr_task_kr_rel")
public class TaskKrRelEntity extends BaseEntity implements Serializable {
    /** 任务ID */
    private String taskId;

    /** KR_ID */
    private String krId;

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
     * KR_ID
     * @return krId
     */
    @Column(name = "kr_id")
    public String getKrId() {
        return krId;
    }

    /**
     * KR_ID
     * @param krId
     */
    public void setKrId(String krId) {
        this.krId = krId;
        addSettedField("krId");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return TaskKrRelEntityMapper.class;
    }
}