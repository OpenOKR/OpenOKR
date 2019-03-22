package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TaskVO extends BaseVO {

    private static final long serialVersionUID = 3900221035323076921L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 任务代码
     */
    @ApiModelProperty(value = "任务代码")
    private String taskCode;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    /**
     * 项目状态：0 临时项目 1 正式项目 2 失效项目
     */
    @ApiModelProperty(value = "项目状态：0 临时项目 1 正式项目 2 失效项目")
    private String taskStatus;

    /**
     * 任务描述
     */
    @ApiModelProperty(value = "任务描述")
    private String taskRemark;

    /**
     * 是否删除: 0 正常 1 删除
     */
    @ApiModelProperty(value = "是否删除: 0 正常 1 删除")
    private String isDeleted;

    /**
     * 任务开始时间
     */
    @ApiModelProperty(value = "任务开始时间")
    private Date taskStartTime;

    /**
     * 任务结束时间
     */
    @ApiModelProperty(value = "任务结束时间")
    private Date taskEndTime;

    /**
     * JIRA标签
     */
    @ApiModelProperty(value = "JIRA标签")
    private String jiraLabel;

    /**
     * 确认人
     */
    @ApiModelProperty(value = "确认人")
    private String confirmUserId;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createUserId;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者名称")
    private String createUserName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTs;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private String updateUserId;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTs;

    /** 所属团队 */
    @ApiModelProperty(value = "所属团队")
    private String belongTeam;

    /** 所属团队 */
    @ApiModelProperty(value = "所属团队名字")
    private String belongTeamName;

    @ApiModelProperty(value = "关联的kr数目")
    private Integer count;

    /**
     * 当前累计耗费工时（h）
     */
    @ApiModelProperty(value = "当前累计耗费工时（h）")
    private BigDecimal totalWorkingHours;

    /** 预计耗时 */
    @ApiModelProperty(value = "预计耗时（h）")
    private BigDecimal estimateTime;
}