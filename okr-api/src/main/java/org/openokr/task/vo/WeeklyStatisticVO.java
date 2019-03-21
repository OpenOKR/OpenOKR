package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yuxinzh
 * @create 2019/3/20
 */
@Data
@Api(value = "周报统计VO")
public class WeeklyStatisticVO extends BaseVO {

    @ApiModelProperty(value = "部门ID，按人员归属统计时展示")
    private String orgId;

    @ApiModelProperty(value = "部门名称，按人员归属统计时展示")
    private String orgName;

    @ApiModelProperty(value = "产品ID，按产品统计时展示")
    private String taskId;

    @ApiModelProperty(value = "产品名称，按产品统计时展示")
    private String taskName;

    @ApiModelProperty(value = "分摊类别ID，一般只在查询饼图时有值")
    private String categoryId;

    @ApiModelProperty(value = "分摊类别名称，一般只在查询饼图时有值")
    private String categoryName;

    @ApiModelProperty(value = "工时")
    private BigDecimal duration;

}
