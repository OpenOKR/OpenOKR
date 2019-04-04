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
@Table(name = "t_okr_apportion_category")
public class ApportionCategoryEntity extends BaseEntity implements Serializable {
    /** 类别名称 */
    private String categoryName;

    private static final long serialVersionUID = 1L;

    /**
     * 类别名称
     * @return categoryName
     */
    @Column(name = "category_name")
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 类别名称
     * @param categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        addSettedField("categoryName");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return ApportionCategoryEntityMapper.class;
    }
}