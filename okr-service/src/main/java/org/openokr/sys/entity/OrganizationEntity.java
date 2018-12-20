package org.openokr.sys.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_sys_organization")
public class OrganizationEntity extends BaseEntity implements Serializable {
    /** 描述 */
    private String description;

    /** 名称 */
    private String name;

    /** 父Id */
    private String parentId;

    /** 公司ID */
    private String companyId;

    /** 组织机构编码 */
    private String code;

    private static final long serialVersionUID = 1L;

    /**
     * 描述
     * @return description
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 名称
     * @return name
     */
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 父Id
     * @return parentId
     */
    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    /**
     * 父Id
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 公司ID
     * @return companyId
     */
    @Column(name = "company_id")
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 公司ID
     * @param companyId
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 组织机构编码
     * @return code
     */
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    /**
     * 组织机构编码
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return OrganizationEntityMapper.class;
    }
}