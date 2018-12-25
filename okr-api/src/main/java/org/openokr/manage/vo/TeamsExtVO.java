package org.openokr.manage.vo;

import java.io.Serializable;

public class TeamsExtVO extends TeamsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    /**
     * 标识当前用户是否可以编辑该团队的目标
     * 0-不可编辑 1-可编辑
     */
    private String editFlag;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }
}