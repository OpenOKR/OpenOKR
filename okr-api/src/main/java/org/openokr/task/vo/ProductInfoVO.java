package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;

public class ProductInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 项目名称
     */
    private String productName;

    /**
     * 父级项目ID
     */
    private String parentProductId;

    /**
     * 类别： 1 项目 2 其他
     */
    private String categoryId;



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
     * 项目名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 项目名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 父级项目ID
     */
    public String getParentProductId() {
        return parentProductId;
    }

    /**
     * 父级项目ID
     */
    public void setParentProductId(String parentProductId) {
        this.parentProductId = parentProductId;
    }

    /**
     * 类别： 1 项目 2 其他
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 类别： 1 项目 2 其他
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

}