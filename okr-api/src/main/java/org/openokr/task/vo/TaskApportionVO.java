package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
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
     * 分摊名称ID
     */
    private String apportionNameId;

    /**
     * 分摊名称
     */
    private String apportionName;

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
     * 分摊名称ID
     */
    public String getApportionNameId() {
        return apportionNameId;
    }

    /**
     * 分摊名称ID
     */
    public void setApportionNameId(String apportionNameId) {
        this.apportionNameId = apportionNameId;
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