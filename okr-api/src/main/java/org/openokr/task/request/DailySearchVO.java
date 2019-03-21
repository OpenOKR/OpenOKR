package org.openokr.task.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.openokr.common.vo.response.PageRequest;

/**
 * 日报查询
 * @author yuxinzh
 * @create 2019/3/1
 */
@Data
@Api(value = "日报查询vo")
public class DailySearchVO extends PageRequest {

    private static final long serialVersionUID = -5140809906435092743L;

    @ApiModelProperty(value = "填报日期字符串，格式yyyy-MM-dd，查询某天时使用，与查询日期区间的条件冲突")
    private String reportDayStr;

    @ApiModelProperty(value = "开始日期字符串，格式yyyy-MM-dd，查询日期区间，查询某天条件冲突")
    private String reportStartDayStr;

    @ApiModelProperty(value = "结束日期字符串，格式yyyy-MM-dd，查询日期区间，查询某天条件冲突")
    private String reportEndDayStr;

    @ApiModelProperty(value = "填写人ID，不用传，后台自动填入当前登录人ID")
    private String reportUserId;

    @ApiModelProperty(value = "项目ID，不筛选不用传")
    private String taskId;

    @ApiModelProperty(value = "审核状态，不筛选不用传")
    private String auditStatus;

    @ApiModelProperty(value = "产品id，不筛选不用传")
    private String productId;

    @ApiModelProperty(value = "分摊id，不筛选不用传")
    private String apportionId;


    @ApiModelProperty(value = "团队id，不筛选不用传")
    private String teamId;

    @ApiModelProperty(value = "okrId，不筛选不用传")
    private String okrId;

}
