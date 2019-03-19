package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TaskDetailVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务详情")
    private TaskVO taskVO;

    @ApiModelProperty(value = "分摊信息集")
    private List<TaskApportionVO> apportionVOS;

    @ApiModelProperty(value = "参与人员信息")
    private List<TaskUserInfoVO> userInfoVOS;

    @ApiModelProperty(value = "个人KR信息")
    private List<TaskKrInfoVO> personKeys;

    @ApiModelProperty(value = "团队KR信息")
    private List<TaskKrInfoVO> teamKeys;

    @ApiModelProperty(value = "公司KR信息")
    private List<TaskKrInfoVO> companyKeys;
}