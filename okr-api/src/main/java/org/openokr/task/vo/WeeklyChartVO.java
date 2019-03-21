package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.openokr.task.vo.chart.LineDataVO;
import org.openokr.task.vo.chart.PieDataVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author yuxinzh
 * @create 2019/3/21
 */
@Api(value = "周报图表VO",description = "WeeklyChartVO")
@Data
public class WeeklyChartVO extends BaseVO {

    @ApiModelProperty(value = "折线图横轴数组")
    private List<String> xAxisData;

    @ApiModelProperty(value = "折线图各组数据名称，与数据list顺序一一对应")
    private List<String> legendData;

    @ApiModelProperty(value = "折线图各组数据，与名称list顺序一一对应")
    private List<LineDataVO> lineSeriesData;

    @ApiModelProperty(value = "饼图各组数据<br>" +
            "示例:<br>" +
            "[\n" +
            "    {value:10, name:'客户定制'},\n" +
            "    {value:9, name:'产品规划'}\n" +
            "]")
    private List<PieDataVO> pieSeriesData;
}
