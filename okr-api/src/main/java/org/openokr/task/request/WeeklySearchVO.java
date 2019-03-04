package org.openokr.task.request;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 周报查询
 * @author yuxinzh
 * @create 2019/3/1
 */
@Data
@Api(value = "周报查询vo")
public class WeeklySearchVO extends BaseVO {

    private static final long serialVersionUID = -5140809906435092743L;

    @ApiModelProperty(value = "填报开始周期字符串，格式yyyy-MM-dd，用于规定开始日期处于这一天之后")
    private String reportStartDateStr;

    @ApiModelProperty(value = "填报结束周期字符串，格式yyyy-MM-dd，用于规定结束日期处于这一天之前")
    private String reportEndDateStr;

    @ApiModelProperty(value = "填报开始周期字符串，格式yyyy-MM-dd，用于强制开始日期一定要是这一天")
    private String reportStartDateStrEquals;

    @ApiModelProperty(value = "填报结束周期字符串，格式yyyy-MM-dd，用于强制结束日期一定要是这一天")
    private String reportEndDateStrEquals;

    @ApiModelProperty(value = "填写人ID，不用传，后台自动填入当前登录人ID")
    private String reportUserId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "关联的任务")
    private String relTaskId;

    @ApiModelProperty(value = "审批状态: 0 待审核 1 已确认 2 已驳回")
    private String auditStatus;
}
