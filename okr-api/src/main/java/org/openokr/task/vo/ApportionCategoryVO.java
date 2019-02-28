package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;

public class ApportionCategoryVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 类别名称
     */
    private String categoryName;



    /**
     * 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 类别名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 类别名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}