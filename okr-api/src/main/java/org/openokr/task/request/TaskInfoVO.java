package org.openokr.task.request;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TaskInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "日期")
    private Date reportDay;

    @ApiModelProperty(value = "工时")
    private BigDecimal duration;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTs;

    @ApiModelProperty(value = "任务开始时间")
    private Date taskStartTime;

    @ApiModelProperty(value = "任务结束时间")
    private Date taskEndTime;

    @ApiModelProperty(value = "JIRA标签(编码)")
    private String jiraLabel;

    @ApiModelProperty(value = "创建者ID")
    private String createUserId;

    @ApiModelProperty(value = "创建者名称")
    private String createUserName;

    @ApiModelProperty(value = "KR信息")
    private List<KrInfoVO> krInfoVOS;

}