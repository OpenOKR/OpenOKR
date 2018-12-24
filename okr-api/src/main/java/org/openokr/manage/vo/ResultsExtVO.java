package org.openokr.manage.vo;

import org.openokr.sys.vo.UserVO;

import java.io.Serializable;
import java.util.List;

public class ResultsExtVO extends ResultsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 参与人员
    private List<UserVO> joinUsers;

    public List<UserVO> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(List<UserVO> joinUsers) {
        this.joinUsers = joinUsers;
    }
}