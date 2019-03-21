package org.openokr.task.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.openokr.common.vo.response.PageRequest;

import java.io.Serializable;
import java.util.Date;

@Data
public class TaskSearchVO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键词")
    private String searchKey;

    @ApiModelProperty(value = "查询开始日期")
    private Date queryStartDate;

    @ApiModelProperty(value = "查询结束日期")
    private Date queryEndDate;

    @ApiModelProperty(value = "当前用户id，前台不用传")
    private String currentUserId;

    @ApiModelProperty(value = "产品Id-全部报工页用")
    private String productId;

    @ApiModelProperty(value = "分摊类别id-全部报工页用")
    private String categoryId;

    @ApiModelProperty(value = "团队id-全部报工页用")
    private String teamId;

    @ApiModelProperty(value = "项目Id-全部报工页用")
    private String taskId;

    @ApiModelProperty(value = "okrId-全部报工页用")
    private String okrId;
}