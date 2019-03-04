package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class TaskSaveVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务详情
     */
    @ApiModelProperty(value = "任务详情")
    private TaskVO taskVO;

    /**
     * 分摊信息集
     */
    @ApiModelProperty(value = "分摊信息集")
    private List<TaskApportionVO> apportionVOS;

    /**
     * 参与人员ID集
     */
    @ApiModelProperty(value = "参与人员ID集")
    private List<String> userIds;

    /**
     * krID集
     */
    @ApiModelProperty(value = "krID集")
    private List<String> krIds;
}