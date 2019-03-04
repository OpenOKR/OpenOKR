package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 报工首页-我的项目信息使用VO
 */
@Data
public class MyTaskCountInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品名")
    private String projectName;

    @ApiModelProperty(value = "成员数")
    private Integer memberNum;

    @ApiModelProperty(value = "任务数")
    private Integer relTaskNum;

    @ApiModelProperty(value = "占用工时")
    private BigDecimal totalDuration;

}