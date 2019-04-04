package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TeamsSearchVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 查询开始时间
     */
    private Date queryStartTime;

    /**
     * 查询结束时间
     */
    private Date queryEndTime;
}