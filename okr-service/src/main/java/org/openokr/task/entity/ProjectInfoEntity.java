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
@Table(name = "t_okr_project_info")
public class ProjectInfoEntity extends BaseEntity implements Serializable {
    /** 项目名称 */
    private String projectName;

    /** 类别： 1 项目 2 其他 */
    private String categoryId;

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     * @return projectName
     */
    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    /**
     * 项目名称
     * @param projectName
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
        addSettedField("projectName");
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
        return ProjectInfoEntityMapper.class;
    }
}