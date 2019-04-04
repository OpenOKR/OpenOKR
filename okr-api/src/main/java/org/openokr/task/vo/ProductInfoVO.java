package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 父级产品ID
     */
    private String parentProductId;

    /**
     * 类别： 1 项目 2 其他
     */
    private String categoryId;

}