package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;

public class OrganizationVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 描述
     */
    private String description;

    /**
     * 名称
     */
    private String name;

    /**
     * 父Id
     */
    private String parentId;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 组织机构编码
     */
    private String code;



    /**
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 父Id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 父Id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 公司ID
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 公司ID
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 组织机构编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 组织机构编码
     */
    public void setCode(String code) {
        this.code = code;
    }

}