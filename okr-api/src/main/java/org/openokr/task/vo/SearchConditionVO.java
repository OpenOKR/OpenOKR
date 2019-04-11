package org.openokr.task.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.openokr.sys.vo.UserVOSimplify;

import java.util.List;

@Data
@Api(value = "全部报工页搜索VO")
public class SearchConditionVO extends ShareVO {


    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "原始项目ID")
    private String temProductId;
    @ApiModelProperty(value = "原始项目名称")
    private String temProductName;

    @ApiModelProperty(value = "原始客户需求Id")
    private String temCustId;

    @ApiModelProperty(value = "原始客户需求名称")
    private String temCustName;

    @ApiModelProperty(value = "项目id")
    private String productId;

    @ApiModelProperty(value = "项目名称")
    private String productName;

    @ApiModelProperty(value = "分摊类别ID")
    private String categoryId;

    @ApiModelProperty(value = "分摊类别名称")
    private String categoryName;

    @ApiModelProperty(value = "团队Id")
    private String teamId;
    @ApiModelProperty(value = "团队名称")
    private String teamName;

    @ApiModelProperty(value = "okr_id")
    private String okrId;

    @ApiModelProperty(value = "okr名称")
    private String okrName;

    @ApiModelProperty(value = "用户id list，前端无需传入数据")
    private List<String> userIdList;

    @ApiModelProperty(value = "是否是管理员,是管理员则传 1")
    private String isAdmin;

    @ApiModelProperty(value = "用户信息 list，团队管理员才会有该列表，返回列表的第一条数据里才会有这个userVOList，前端无需传入数据")
    private List<UserVOSimplify> userVOList;

}