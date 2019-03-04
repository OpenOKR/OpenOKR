package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.openokr.common.constant.WeeklyConstants;

import java.util.Date;
import java.math.BigDecimal;

@Data
@Api(value = "周报VO")
public class WeeklyVO extends BaseVO  {

    private static final long serialVersionUID = 4192143588145761177L;

    @ApiModelProperty(value = "主键，新增不传")
    private String id;

    @ApiModelProperty(value = "填写人")
    private String reportUserId;

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "关联的任务")
    private String relTaskId;

    @ApiModelProperty(value = "填报开始周期")
    private Date reportStartDate;

    @ApiModelProperty(value = "填报结束周期")
    private Date reportEndDate;

    @ApiModelProperty(value = "耗费总工时")
    private BigDecimal duration;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "审批状态: 0 待审核 1 已确认 2 已驳回")
    private String auditStatus;

    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;

    @ApiModelProperty(value = "创建者")
    private String createUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTs;

    @ApiModelProperty(value = "更新者")
    private String updateUserId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTs;

    @ApiModelProperty(value = "项目名称，展示用")
    private String taskName;

    @ApiModelProperty(value = "审批状态名称，展示用")
    public String getAuditStatusStr(){
        return StringUtils.isBlank(auditStatus)?"": WeeklyConstants.AUDIT_STATUS_MAP.get(auditStatus);
    }

}