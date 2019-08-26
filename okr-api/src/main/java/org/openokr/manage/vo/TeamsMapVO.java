package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TeamsMapVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 层级，公司或员工
     */
    private String layer;

    /**
     * 标签，公司名称或员工名称
     */
    private String label;

    /**
     * 进度
     */
    private String progress;

    /**
     * 目标
     */
    private List<ObjectiveVO> objectives;

    private String showContent = "true";

    private List<TeamsMapVO> children;


}