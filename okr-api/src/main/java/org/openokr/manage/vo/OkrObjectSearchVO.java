package org.openokr.manage.vo;

import org.openokr.application.Global;

import java.io.Serializable;

/**
 * OKR目标查询VO
 */
public class OkrObjectSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    //团队ID
    private String teamId;

    //目标ID
    private String objectId;

    //OKR类型 1-个人 2-团队 3-公司
    private String type;

    private String keyword;

    private String executeStatus;

    private Integer limitAmount;

    private String timeSessionId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLimitAmount() {
        return limitAmount == null ? Global.OKR.limitAmount : limitAmount;
    }

    public void setLimitAmount(Integer limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTimeSessionId() {
        return timeSessionId;
    }

    public void setTimeSessionId(String timeSessionId) {
        this.timeSessionId = timeSessionId;
    }
}