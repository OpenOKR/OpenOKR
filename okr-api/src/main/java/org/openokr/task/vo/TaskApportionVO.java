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

}