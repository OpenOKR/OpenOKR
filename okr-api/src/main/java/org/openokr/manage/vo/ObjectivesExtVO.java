package org.openokr.manage.vo;

import org.openokr.manage.enumerate.ObjectivesTypeEnum;
import org.openokr.sys.vo.UserVO;

import java.io.Serializable;
import java.util.List;

public class ObjectivesExtVO extends ObjectivesVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String typeName;

    // 团队名称
    private String teamName;

    // 参与人员
    private List<UserVO> joinUsers;

    private List<ResultsExtVO> resultsExtList;

    public String getTypeName() {
        return ObjectivesTypeEnum.getByCode(getType()).getName();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<UserVO> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(List<UserVO> joinUsers) {
        this.joinUsers = joinUsers;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<ResultsExtVO> getResultsExtList() {
        return resultsExtList;
    }

    public void setResultsExtList(List<ResultsExtVO> resultsExtList) {
        this.resultsExtList = resultsExtList;
    }
}