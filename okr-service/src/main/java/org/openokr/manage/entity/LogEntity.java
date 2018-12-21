package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_okr_manage_log")
public class LogEntity extends BaseEntity implements Serializable {
    /** 消息内容 */
    private String message;

    /** 业务类型 1-目标 2-关键结果 */
    private String bizType;

    /** 业务id */
    private String bizId;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    private static final long serialVersionUID = 1L;

    /**
     * 消息内容
     * @return message
     */
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    /**
     * 消息内容
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
        addSettedField("message");
    }

    /**
     * 业务类型 1-目标 2-关键结果
     * @return bizType
     */
    @Column(name = "biz_type")
    public String getBizType() {
        return bizType;
    }

    /**
     * 业务类型 1-目标 2-关键结果
     * @param bizType
     */
    public void setBizType(String bizType) {
        this.bizType = bizType;
        addSettedField("bizType");
    }

    /**
     * 业务id
     * @return bizId
     */
    @Column(name = "biz_id")
    public String getBizId() {
        return bizId;
    }

    /**
     * 业务id
     * @param bizId
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
        addSettedField("bizId");
    }

    /**
     * 创建时间
     * @return createTs
     */
    @Column(name = "create_ts")
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建时间
     * @param createTs
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
        addSettedField("createTs");
    }

    /**
     * 创建者
     * @return createUserId
     */
    @Column(name = "create_user_id")
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建者
     * @param createUserId
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
        addSettedField("createUserId");
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return LogEntityMapper.class;
    }
}