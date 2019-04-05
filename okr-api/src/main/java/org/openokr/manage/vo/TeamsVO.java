package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class TeamsVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 图标url
     */
    private String icon;

    /**
     * 负责人id
     */
    private String ownerId;

    /**
     * 父团队
     */
    private String parentId;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private Date updateTs;

    /**
     * 更新者
     */
    private String updateUserId;

    /**
     * 团队类型 1.公司团队 2.其他团队
     */
    private String type;


}