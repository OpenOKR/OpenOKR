package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 日报统计VO
 * @author yuxinzh
 * @create 2019/3/4
 */
@Data
@Api(value = "日报统计VO")
public class DailyStasticsVO extends BaseVO {

    @ApiModelProperty(value = "耗费总工时")
    private BigDecimal costTimeNum;

    @ApiModelProperty(value = "任务总数")
    private BigDecimal taskNum;

    @ApiModelProperty(value = "产品总数")
    private BigDecimal productNum;

    @ApiModelProperty(value = "人员总数")
    private BigDecimal personnelNum;
}
