package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class ResultUserRelaVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 关键结果id
     */
    private String resultId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 状态 1.待确认 2.确认 3.拒绝
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 创建者
     */
    private String createUserId;



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
     * 关键结果id
     */
    public String getResultId() {
        return resultId;
    }

    /**
     * 关键结果id
     */
    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    /**
     * 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 状态 1.待确认 2.确认 3.拒绝
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态 1.待确认 2.确认 3.拒绝
     */
    public void setStatus(String status) {
        this.status = status;
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

}