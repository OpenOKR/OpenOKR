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
    private BigDecimal countDuration;

    @ApiModelProperty(value = "参与总人数")
    private BigDecimal countPeople;

    @ApiModelProperty(value = "包含项目数")
    private BigDecimal countTask;

    @ApiModelProperty(value = "包含任务数，暂时不知道怎么算")
    private BigDecimal countRelTask;
}
