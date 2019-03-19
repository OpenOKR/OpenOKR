package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskKrInfoVO extends BaseVO {

    private static final long serialVersionUID = 3900221035323076921L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "kr名称")
    private String text;

    @ApiModelProperty(value = "统计")
    private Integer count;
}