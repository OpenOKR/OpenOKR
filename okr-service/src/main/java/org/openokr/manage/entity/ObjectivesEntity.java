package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_manage_objectives")
public class ObjectivesEntity extends BaseEntity implements Serializable {
    /** 时间段id */
    private String timeSessionId;

    /** 名称 */
    private String name;

    /** 描述 */
    private String description;

    /** 负责人id */
    private String ownerId;

    /** 父目标 */
    private String parentId;

    /** 信心指数 */
    private String confidenceLevel;

    /** 公开类型 1-公开 2-团队公开 3-保密 */
    private String visibility;

    /** 类型 1-个人 2-团队 3-公司 */
    private String type;

    /** 确认状态 1.未提交，2.待确认，3.已确认，4.被驳回 */
    private String status;

    /** 删除标识 0-否 1-是 */
    private String delFlag;

    /** 当前进度（百分比) */
    private BigDecimal progress;

    /** 评分（季度结束的时候要对O的完成情况进行评分和自评） */
    private String score;

    /** 评分说明（季度结束的时候要对O的完成情况进行评分和自评） */
    private String assessment;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    /** 更新时间 */
    private Date updateTs;

    /** 更新者 */
    private String updateUserId;

    /** 团队ID(type为团队和公司的时候才存储) */
    private String teamId;

    /** 排序 */
    private Integer sort;

    private static final long serialVersionUID = 1L;

    /**
     * 时间段id
     * @return timeSessionId
     */
    @Column(name = "time_session_id")
    public String getTimeSessionId() {
        return timeSessionId;
    }

    /**
     * 时间段id
     * @param timeSessionId
     */
    public void setTimeSessionId(String timeSessionId) {
        this.timeSessionId = timeSessionId;
        addSettedField("timeSessionId");
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
        addSettedField("name");
    }

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
        addSettedField("description");
    }

    /**
     * 负责人id
     * @return ownerId
     */
    @Column(name = "owner_id")
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 负责人id
     * @param ownerId
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        addSettedField("ownerId");
    }

    /**
     * 父目标
     * @return parentId
     */
    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    /**
     * 父目标
     * @param parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
        addSettedField("parentId");
    }

    /**
     * 信心指数
     * @return confidenceLevel
     */
    @Column(name = "confidence_level")
    public String getConfidenceLevel() {
        return confidenceLevel;
    }

    /**
     * 信心指数
     * @param confidenceLevel
     */
    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
        addSettedField("confidenceLevel");
    }

    /**
     * 公开类型 1-公开 2-团队公开 3-保密
     * @return visibility
     */
    @Column(name = "visibility")
    public String getVisibility() {
        return visibility;
    }

    /**
     * 公开类型 1-公开 2-团队公开 3-保密
     * @param visibility
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
        addSettedField("visibility");
    }

    /**
     * 类型 1-个人 2-团队 3-公司
     * @return type
     */
    @Column(name = "type")
    public String getType() {
        return type;
    }

    /**
     * 类型 1-个人 2-团队 3-公司
     * @param type
     */
    public void setType(String type) {
        this.type = type;
        addSettedField("type");
    }

    /**
     * 确认状态 1.未提交，2.待确认，3.已确认，4.被驳回
     * @return status
     */
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    /**
     * 确认状态 1.未提交，2.待确认，3.已确认，4.被驳回
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
        addSettedField("status");
    }

    /**
     * 删除标识 0-否 1-是
     * @return delFlag
     */
    @Column(name = "del_flag")
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标识 0-否 1-是
     * @param delFlag
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
        addSettedField("delFlag");
    }

    /**
     * 当前进度（百分比)
     * @return progress
     */
    @Column(name = "progress")
    public BigDecimal getProgress() {
        return progress;
    }

    /**
     * 当前进度（百分比)
     * @param progress
     */
    public void setProgress(BigDecimal progress) {
        this.progress = progress;
        addSettedField("progress");
    }

    /**
     * 评分（季度结束的时候要对O的完成情况进行评分和自评）
     * @return score
     */
    @Column(name = "score")
    public String getScore() {
        return score;
    }

    /**
     * 评分（季度结束的时候要对O的完成情况进行评分和自评）
     * @param score
     */
    public void setScore(String score) {
        this.score = score;
        addSettedField("score");
    }

    /**
     * 评分说明（季度结束的时候要对O的完成情况进行评分和自评）
     * @return assessment
     */
    @Column(name = "assessment")
    public String getAssessment() {
        return assessment;
    }

    /**
     * 评分说明（季度结束的时候要对O的完成情况进行评分和自评）
     * @param assessment
     */
    public void setAssessment(String assessment) {
        this.assessment = assessment;
        addSettedField("assessment");
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
     * 更新时间
     * @return updateTs
     */
    @Column(name = "update_ts")
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新时间
     * @param updateTs
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
        addSettedField("updateTs");
    }

    /**
     * 更新者
     * @return updateUserId
     */
    @Column(name = "update_user_id")
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新者
     * @param updateUserId
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
        addSettedField("updateUserId");
    }

    /**
     * 团队ID(type为团队和公司的时候才存储)
     * @return teamId
     */
    @Column(name = "team_id")
    public String getTeamId() {
        return teamId;
    }

    /**
     * 团队ID(type为团队和公司的时候才存储)
     * @param teamId
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
        addSettedField("teamId");
    }

    /**
     * 排序
     * @return sort
     */
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
        addSettedField("sort");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return ObjectivesEntityMapper.class;
    }
}