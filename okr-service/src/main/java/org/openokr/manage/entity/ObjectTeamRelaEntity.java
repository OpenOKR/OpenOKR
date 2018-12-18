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
@Table(name = "t_okr_manage_object_team_rela")
public class ObjectTeamRelaEntity extends BaseEntity implements Serializable {
    /** 目标id */
    private String objectId;

    /** 团队id */
    private String teamId;

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
    }

    /**
     * 团队id
     * @return teamId
     */
    @Column(name = "team_id")
    public String getTeamId() {
        return teamId;
    }

    /**
     * 团队id
     * @param teamId
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
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
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return ObjectTeamRelaEntityMapper.class;
    }
}