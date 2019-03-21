package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.openokr.sys.vo.UserVO;

import java.math.BigDecimal;
import java.util.List;

@Data
@Api(value = "我的团队VO")
public class TeamTaskCountInfoVO extends BaseVO {

    private static final long serialVersionUID = 1893549377995858800L;

    @ApiModelProperty(value = "团队名")
    private String teamName;

    @ApiModelProperty(value = "团队成员数")
    private Integer teamMemberNum;

    @ApiModelProperty(value = "关联任务数")
    private Integer relTaskNum;

    @ApiModelProperty(value = "当前累计耗费工时（h）")
    private BigDecimal totalWorkingHours;

    @ApiModelProperty(value = "团队成员信息")
    private List<UserVO> taskUserInfoVOS;

    @ApiModelProperty(value = "关联任务名")
    private List<String> relTaskNames;
}