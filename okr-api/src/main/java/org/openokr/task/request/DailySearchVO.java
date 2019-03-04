package org.openokr.task.request;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 日报查询
 * @author yuxinzh
 * @create 2019/3/1
 */
@Data
@Api(value = "日报查询vo")
public class DailySearchVO extends BaseVO {

    private static final long serialVersionUID = -5140809906435092743L;

    @ApiModelProperty(value = "填报日期字符串，格式yyyy-MM-dd")
    private String reportDayStr;
}
