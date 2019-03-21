package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yuxinzh
 * @create 2019/3/20
 */
@Data
@Api(value = "周报统计VO")
public class WeeklyStatisticVO extends BaseVO {

    @ApiModelProperty(value = "团队ID，按人员归属统计时展示")
    private String teamId;

    @ApiModelProperty(value = "团队名称，按人员归属统计时展示")
    private String teamName;

    @ApiModelProperty(value = "产品ID，按产品统计时展示")
    private String taskId;

    @ApiModelProperty(value = "产品名称，按产品统计时展示")
    private String taskName;

    @ApiModelProperty(value = "分摊类别ID，一般只在查询饼图时有值")
    private String categoryId;

    @ApiModelProperty(value = "分摊类别名称，一般只在查询饼图时有值")
    private String categoryName;

    @ApiModelProperty(value = "工时")
    private Double duration;

}
