package org.openokr.sys.vo.request;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSelectUserVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /**
     * 部门名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

}