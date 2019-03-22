package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserRoleVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 
     */
    private String createUserId;

    /**
     * 
     */
    private Date createTs;

    /**
     * 
     */
    private String updateUserId;

    /**
     * 
     */
    private Date updateTs;




}