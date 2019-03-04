package org.openokr.task.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_task_apportion")
public class TaskApportionEntity extends BaseEntity implements Serializable {
    /** 任务ID */
    private String taskId;

    /** 项目ID */
    private String projectId;

    /** 类别ID */
    private String categoryId;

    /** 分摊比例 */
    private BigDecimal apportionRate;

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
     * 项目ID
     * @return projectId
     */
    @Column(name = "project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * 项目ID
     * @param projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
        addSettedField("projectId");
    }

    /**
     * 类别ID
     * @return categoryId
     */
    @Column(name = "category_id")
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 类别ID
     * @param categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        addSettedField("categoryId");
    }

    /**
     * 分摊比例
     * @return apportionRate
     */
    @Column(name = "apportion_rate")
    public BigDecimal getApportionRate() {
        return apportionRate;
    }

    /**
     * 分摊比例
     * @param apportionRate
     */
    public void setApportionRate(BigDecimal apportionRate) {
        this.apportionRate = apportionRate;
        addSettedField("apportionRate");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return TaskApportionEntityMapper.class;
    }
}