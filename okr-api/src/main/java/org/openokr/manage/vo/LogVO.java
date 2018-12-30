package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class LogVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 业务类型 1-目标 2-关键结果
     */
    private String bizType;

    /**
     * 业务id
     */
    private String bizId;

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
     * 消息内容
     */
    public String getMessage() {
        return message;
    }

    /**
     * 消息内容
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 业务类型 1-目标 2-关键结果
     */
    public String getBizType() {
        return bizType;
    }

    /**
     * 业务类型 1-目标 2-关键结果
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    /**
     * 业务id
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * 业务id
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
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