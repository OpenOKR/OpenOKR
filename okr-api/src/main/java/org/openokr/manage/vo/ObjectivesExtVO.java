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

    //关键结果
    private List<ResultsExtVO> resultsExtList;

    // 历史操作记录
    private List<LogVO> operateRecordList;

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

    public List<LogVO> getOperateRecordList() {
        return operateRecordList;
    }

    public void setOperateRecordList(List<LogVO> operateRecordList) {
        this.operateRecordList = operateRecordList;
    }
}