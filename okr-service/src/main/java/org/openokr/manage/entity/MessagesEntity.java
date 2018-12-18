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
@Table(name = "t_okr_messages")
public class MessagesEntity extends BaseEntity implements Serializable {
    /** 用户id */
    private String userId;

    /** 标题 */
    private String title;

    /** 消息内容 */
    private String content;

    /** 类型 1.一般文本 2.目标审核 3.协同审核 4.目标跳转 */
    private String type;

    /** 关联id */
    private String targetId;

    /** 是否已处理 0-否，1-是 */
    private String isProcessed;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    /** 更新时间 */
    private Date updateTs;

    /** 更新者 */
    private String updateUserId;

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     * @return userId
     */
    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 标题
     * @return title
     */
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 消息内容
     * @return content
     */
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    /**
     * 消息内容
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 类型 1.一般文本 2.目标审核 3.协同审核 4.目标跳转
     * @return type
     */
    @Column(name = "type")
    public String getType() {
        return type;
    }

    /**
     * 类型 1.一般文本 2.目标审核 3.协同审核 4.目标跳转
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 关联id
     * @return targetId
     */
    @Column(name = "target_id")
    public String getTargetId() {
        return targetId;
    }

    /**
     * 关联id
     * @param targetId
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * 是否已处理 0-否，1-是
     * @return isProcessed
     */
    @Column(name = "is_processed")
    public String getIsProcessed() {
        return isProcessed;
    }

    /**
     * 是否已处理 0-否，1-是
     * @param isProcessed
     */
    public void setIsProcessed(String isProcessed) {
        this.isProcessed = isProcessed;
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
    }

    /**
     * 更新时间
     * @return updateTs
     */
    @Column(name = "update_ts")
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新时间
     * @param updateTs
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 更新者
     * @return updateUserId
     */
    @Column(name = "update_user_id")
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新者
     * @param updateUserId
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 获得当前实体 Mapper Class
     * @return Class
     */
    @Override
    public Class<?> obtainEntityMapperClass() {
        return MessagesEntityMapper.class;
    }
}