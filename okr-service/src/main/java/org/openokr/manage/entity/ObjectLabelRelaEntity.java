package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_manage_object_label_rela")
public class ObjectLabelRelaEntity extends BaseEntity implements Serializable {
    /** 目标id */
    private String objectId;

    /** 标签id */
    private String labelId;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    private static final long serialVersionUID = 1L;

    /**
     * 目标id
     * @return objectId
     */
    @Column(name = "object_id")
    public String getObjectId() {
        return objectId;
    }

    /**
     * 目标id
     * @param objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
        addSettedField("objectId");
    }

    /**
     * 标签id
     * @return labelId
     */
    @Column(name = "label_id")
    public String getLabelId() {
        return labelId;
    }

    /**
     * 标签id
     * @param labelId
     */
    public void setLabelId(String labelId) {
        this.labelId = labelId;
        addSettedField("labelId");
    }

    /**
     * 创建时间
     * @return createTs
     */
    @Column(name = "create_ts")
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建时间
     * @param createTs
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
        addSettedField("createTs");
    }

    /**
     * 创建者
     * @return createUserId
     */
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建者
     * @param createUserId
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
        addSettedField("createUserId");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return ObjectLabelRelaEntityMapper.class;
    }
}