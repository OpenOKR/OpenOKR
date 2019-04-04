package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerVO extends BaseVO implements Serializable {

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
     * 类型： 00、规模性园区 01、政府开发区
     */
    private String type;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 更新者
     */
    private String updateUserId;

    /**
     * 更新时间
     */
    private Date updateTs;

}