package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TaskUserInfoVO extends BaseVO {

    private static final long serialVersionUID = 3900221035323076921L;

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String realName;

}