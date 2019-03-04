package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

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

}