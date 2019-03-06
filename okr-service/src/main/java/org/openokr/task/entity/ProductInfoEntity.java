package org.openokr.task.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_product_info")
public class ProductInfoEntity extends BaseEntity implements Serializable {
    /** 项目名称 */
    private String productName;

    /** 父级项目ID */
    private String parentProductId;

    /** 类别： 1 项目 2 其他 */
    private String categoryId;

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     * @return productName
     */
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    /**
     * 项目名称
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
        addSettedField("productName");
    }

    /**
     * 父级项目ID
     * @return parentProductId
     */
    @Column(name = "parent_product_id")
    public String getParentProductId() {
        return parentProductId;
    }

    /**
     * 父级项目ID
     * @param parentProductId
     */
    public void setParentProductId(String parentProductId) {
        this.parentProductId = parentProductId;
        addSettedField("parentProductId");
    }

    /**
     * 类别： 1 项目 2 其他
     * @return categoryId
     */
    @Column(name = "category_id")
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 类别： 1 项目 2 其他
     * @param categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        addSettedField("categoryId");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return ProductInfoEntityMapper.class;
    }
}