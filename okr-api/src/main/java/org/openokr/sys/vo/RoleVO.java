package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class RoleVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建日期
     */
    private Date createTs;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 更新日期
     */
    private Date updateTs;

    /**
     * 更新人id
     */
    private String updateUserId;


}