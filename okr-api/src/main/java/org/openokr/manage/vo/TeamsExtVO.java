package org.openokr.manage.vo;

import lombok.Data;
import org.openokr.sys.vo.UserVO;

import java.io.Serializable;
import java.util.List;
@Data
public class TeamsExtVO extends TeamsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    /**
     * 标识当前用户是否可以编辑该团队的目标
     * 0-不可编辑 1-可编辑
     */
    private String editFlag;

    private String parentName;

    // 团队成员
    private List<String> teamRelUserIds;
    private List<UserVO> teamRelUsers;


}