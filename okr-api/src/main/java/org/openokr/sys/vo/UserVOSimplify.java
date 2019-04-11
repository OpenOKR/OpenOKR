package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVOSimplify extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    @ApiModelProperty(value = "用户名")

    /**
     * 用户名
     */
    private String userName;

    @ApiModelProperty(value = "真实姓名")

    /**
     * 真实姓名
     */
    private String realName;

    @ApiModelProperty(value = "角色类型  00：超级管理员 01：系统管理员 02：普通管理员   10：用户")
    private String roleType;





}