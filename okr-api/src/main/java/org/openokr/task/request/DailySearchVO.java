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

    @ApiModelProperty(value = "填报日期字符串，格式yyyy-MM-dd，精确到日时使用，与时间范围的条件冲突")
    private String reportDayStr;

    @ApiModelProperty(value = "开始日期字符串，格式yyyy-MM-dd，精确到时间范围时使用，与日的条件冲突")
    private String reportStartDayStr;

    @ApiModelProperty(value = "结束日期字符串，格式yyyy-MM-dd，精确到时间范围时使用，与日的条件冲突")
    private String reportEndDayStr;

    @ApiModelProperty(value = "填写人ID，不用传，后台自动填入当前登录人ID")
    private String reportUserId;

    @ApiModelProperty(value = "项目ID")
    private String taskId;


}
