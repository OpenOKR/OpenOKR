package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class OkrMapVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 层级，公司，团队或个人
     */
    private String layer;

    /**
     * 公司，团队或员工名称
     */
    private String orgName;

    /**
     * 进度
     */
    private String progress = "";

    /**
     * 目标
     */
    private String objective = "";

    /**
     * 前端使用
     */
    private Boolean showContent = false;

    private List<ObjectiveVO> keys = new ArrayList<>();

    private List<OkrMapVO> children;


}