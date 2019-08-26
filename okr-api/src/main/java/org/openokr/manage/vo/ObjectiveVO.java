package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ObjectiveVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    /**
     * 目标内容
     */
    private String content;

}