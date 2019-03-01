package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.math.BigDecimal;

public class TaskApportionVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 分摊ID
     */
    private String apportionId;

    /**
     * 类别ID
     */
    private String categoryId;

    /**
     * 分摊比例
     */
    private BigDecimal apportionRate;



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
     * 分摊ID
     */
    public String getApportionId() {
        return apportionId;
    }

    /**
     * 分摊ID
     */
    public void setApportionId(String apportionId) {
        this.apportionId = apportionId;
    }

    /**
     * 类别ID
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 类别ID
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 分摊比例
     */
    public BigDecimal getApportionRate() {
        return apportionRate;
    }

    /**
     * 分摊比例
     */
    public void setApportionRate(BigDecimal apportionRate) {
        this.apportionRate = apportionRate;
    }

}