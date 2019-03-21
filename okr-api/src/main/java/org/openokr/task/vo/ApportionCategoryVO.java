package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@Api(value = "分摊类别vo")
public class ApportionCategoryVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -5297353809366943821L;
    
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "类别名称")
    private String categoryName;

}