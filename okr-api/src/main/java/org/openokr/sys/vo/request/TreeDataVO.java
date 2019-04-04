package org.openokr.sys.vo.request;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeDataVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "主键ID")
    private String label;

    @ApiModelProperty(value = "类型 00:部门 01:人员")
    private String type;

    @ApiModelProperty(value = "下一级")
    private List<TreeDataVO> children;

}