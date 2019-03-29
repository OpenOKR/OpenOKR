package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description :
 * @author: cww
 * @DateTime: 2019/3/29 19:20
 */
@Data
public class ShareVO extends BaseVO {
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "团队名称")
    private String teamName;
}
