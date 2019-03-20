package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.openokr.common.constant.DailyConstant;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Api(value = "日报VO")
public class DailyVO extends BaseVO {

    private static final long serialVersionUID = 1893549377995858800L;

    @ApiModelProperty(value = "主键，新增不传")
    private String id;

    @ApiModelProperty(value = "填写人id")
    private String reportUserId;

    @ApiModelProperty(value = "填写人姓名")
    private String reportUserName;

    @ApiModelProperty(value = "项目ID")
    private String taskId;

    @ApiModelProperty(value = "填报日期")
    private Date reportDay;

    @ApiModelProperty(value = "报工时长")
    private BigDecimal duration;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "创建者，展示用")
    private String createUserId;

    @ApiModelProperty(value = "创建时间，展示用")
    private Date createTs;

    @ApiModelProperty(value = "更新者，展示用")
    private String updateUserId;

    @ApiModelProperty(value = "更新时间，展示用")
    private Date updateTs;

    @ApiModelProperty(value = "项目名称，展示用")
    private String taskName;

    @ApiModelProperty(value = "审核状态，展示用")
    private String auditStatusStr;

    @ApiModelProperty(value = "审核状态 00待审核、01已确认、02已驳回")
    private String auditStatus;


    public String getAuditStatusStr() {
        if (auditStatus!=null){
            return DailyConstant.DAILY_AUDIT_STATUS_MAP.get(auditStatus);
        }else {
            return DailyConstant.DAILY_AUDIT_STATUS_MAP.get(DailyConstant.DAILY_AUDIT_STATUS_WAITING);
        }
    }

}