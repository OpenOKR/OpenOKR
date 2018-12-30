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
@Table(name = "t_okr_team_user_rela")
public class TeamUserRelaEntity extends BaseEntity implements Serializable {
    /** 团队id */
    private String teamId;

    /** 用户id */
    private String userId;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    private static final long serialVersionUID = 1L;

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
        addSettedField("teamId");
    }

    /**
     * 用户id
     * @return userId
     */
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
        addSettedField("userId");
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
        return TeamUserRelaEntityMapper.class;
    }
}