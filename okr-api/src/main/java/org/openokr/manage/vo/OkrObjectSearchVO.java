package org.openokr.manage.vo;

import java.io.Serializable;

/**
 * OKR目标查询VO
 */
public class OkrObjectSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    //OKR类型 1-个人 2-团队 3-公司
    private String type;

    private String keyword;

    private String executeStatus;

    // 查询个数限制
    private Integer limitAmount;

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
        return limitAmount;
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
}