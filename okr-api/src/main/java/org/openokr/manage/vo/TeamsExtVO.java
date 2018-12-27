package org.openokr.manage.vo;

import org.openokr.sys.vo.UserVO;

import java.io.Serializable;
import java.util.List;

public class TeamsExtVO extends TeamsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    /**
     * 标识当前用户是否可以编辑该团队的目标
     * 0-不可编辑 1-可编辑
     */
    private String editFlag;

    // 团队成员
    private List<UserVO> teamRelUsers;

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

    public List<UserVO> getTeamRelUsers() {
        return teamRelUsers;
    }

    public void setTeamRelUsers(List<UserVO> teamRelUsers) {
        this.teamRelUsers = teamRelUsers;
    }
}