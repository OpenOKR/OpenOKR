package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码加密 盐
     */
    private String salt;

    /**
     * 是否激活 1-激活，0-失效
     */
    private Boolean active;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 邮件
     */
    private String email;

    /**
     * 真是姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 机构Id
     */
    private String organizationId;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建日期
     */
    private Date createTs;

    /**
     * 更新人id
     */
    private String updateUserId;

    /**
     * 更新日期
     */
    private Date updateTs;

    /**
     * 个人头像URL地址
     */
    private String photoUrl;

    /**
     * 角色Id
     */
    private String roleId;
    /**
     * 角色Id
     */
    private String roleName;

    /**
     * 部门
     */
    private String organizationName;


    @ApiModelProperty(value = "角色类型  00：超级管理员 01：系统管理员 02：普通管理员   10：用户")
    private String roleType;





}