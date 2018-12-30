package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ObjectivesVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 时间段id
     */
    private String timeSessionId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 负责人id
     */
    private String ownerId;

    /**
     * 父目标
     */
    private String parentId;

    /**
     * 信心指数
     */
    private String confidenceLevel;

    /**
     * 公开类型 1-公开 2-团队公开 3-保密
     */
    private String visibility;

    /**
     * 类型 1-个人 2-团队 3-公司
     */
    private String type;

    /**
     * 确认状态 1.未提交，2.待确认，3.已确认，4.被驳回
     */
    private String status;

    /**
     * 删除标识 0-否 1-是
     */
    private String delFlag;

    /**
     * 当前进度（百分比)
     */
    private BigDecimal progress;

    /**
     * 评分（季度结束的时候要对O的完成情况进行评分和自评）
     */
    private String score;

    /**
     * 评分说明（季度结束的时候要对O的完成情况进行评分和自评）
     */
    private String assessment;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private Date updateTs;

    /**
     * 更新者
     */
    private String updateUserId;

    /**
     * 团队ID(type为团队和公司的时候才存储)
     */
    private String teamId;



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
     * 时间段id
     */
    public String getTimeSessionId() {
        return timeSessionId;
    }

    /**
     * 时间段id
     */
    public void setTimeSessionId(String timeSessionId) {
        this.timeSessionId = timeSessionId;
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
     * 负责人id
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 负责人id
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 父目标
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 父目标
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 信心指数
     */
    public String getConfidenceLevel() {
        return confidenceLevel;
    }

    /**
     * 信心指数
     */
    public void setConfidenceLevel(String confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    /**
     * 公开类型 1-公开 2-团队公开 3-保密
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * 公开类型 1-公开 2-团队公开 3-保密
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    /**
     * 类型 1-个人 2-团队 3-公司
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 1-个人 2-团队 3-公司
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 确认状态 1.未提交，2.待确认，3.已确认，4.被驳回
     */
    public String getStatus() {
        return status;
    }

    /**
     * 确认状态 1.未提交，2.待确认，3.已确认，4.被驳回
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 删除标识 0-否 1-是
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标识 0-否 1-是
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 当前进度（百分比)
     */
    public BigDecimal getProgress() {
        return progress;
    }

    /**
     * 当前进度（百分比)
     */
    public void setProgress(BigDecimal progress) {
        this.progress = progress;
    }

    /**
     * 评分（季度结束的时候要对O的完成情况进行评分和自评）
     */
    public String getScore() {
        return score;
    }

    /**
     * 评分（季度结束的时候要对O的完成情况进行评分和自评）
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * 评分说明（季度结束的时候要对O的完成情况进行评分和自评）
     */
    public String getAssessment() {
        return assessment;
    }

    /**
     * 评分说明（季度结束的时候要对O的完成情况进行评分和自评）
     */
    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    /**
     * 创建时间
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建时间
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 创建者
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建者
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新时间
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 更新者
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新者
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 团队ID(type为团队和公司的时候才存储)
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * 团队ID(type为团队和公司的时候才存储)
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

}