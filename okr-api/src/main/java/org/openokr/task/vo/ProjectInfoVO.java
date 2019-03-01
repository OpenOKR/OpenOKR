package org.openokr.task.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;

public class ProjectInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 父级项目ID
     */
    private String parentProjectId;

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
    public String getProjectName() {
        return projectName;
    }

    /**
     * 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 父级项目ID
     */
    public String getParentProjectId() {
        return parentProjectId;
    }

    /**
     * 父级项目ID
     */
    public void setParentProjectId(String parentProjectId) {
        this.parentProjectId = parentProjectId;
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