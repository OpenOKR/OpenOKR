package org.openokr.task.vo.chart;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yuxinzh
 * @create 2019/3/22
 */
@Data
@Api(value = "饼图数据VO",description = "PieDataVO")
public class PieDataVO extends BaseVO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "饼图数据数组")
    private BigDecimal value;
}
