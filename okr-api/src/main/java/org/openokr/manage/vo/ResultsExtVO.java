package org.openokr.manage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.openokr.sys.vo.UserVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ResultsExtVO extends ResultsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String objectName;

    // 参与人员(协同者)
    private List<UserVO> joinUsers;

    @Override
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    public Date getEndTs() {
        return super.getEndTs();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<UserVO> getJoinUsers() {
        return joinUsers;
    }

    public void setJoinUsers(List<UserVO> joinUsers) {
        this.joinUsers = joinUsers;
    }

}