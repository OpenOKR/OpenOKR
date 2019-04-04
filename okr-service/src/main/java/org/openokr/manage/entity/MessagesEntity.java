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

    /** 是否已读 0.未读 1.已读 */
    private String isRead;

    /** 消息类型标记 1.提交审核、修改KR等等 2、审核通过、同意等等 3、审核不通过、不同意 4、系统消息 */
    private String mark;

    /** 备注 */
    private String remarks;

    /** 删除标识 0-否 1-是 */
    private String delFlag;

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
        addSettedField("userId");
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
        addSettedField("title");
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
        addSettedField("content");
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
        addSettedField("type");
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
        addSettedField("targetId");
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
        addSettedField("isProcessed");
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
        addSettedField("updateTs");
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
        addSettedField("updateUserId");
    }

    /**
     * 是否已读 0.未读 1.已读
     * @return isRead
     */
    @Column(name = "is_read")
    public String getIsRead() {
        return isRead;
    }

    /**
     * 是否已读 0.未读 1.已读
     * @param isRead
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead;
        addSettedField("isRead");
    }

    /**
     * 消息类型标记 1.提交审核、修改KR等等 2、审核通过、同意等等 3、审核不通过、不同意 4、系统消息
     * @return mark
     */
    @Column(name = "mark")
    public String getMark() {
        return mark;
    }

    /**
     * 消息类型标记 1.提交审核、修改KR等等 2、审核通过、同意等等 3、审核不通过、不同意 4、系统消息
     * @param mark
     */
    public void setMark(String mark) {
        this.mark = mark;
        addSettedField("mark");
    }

    /**
     * 备注
     * @return remarks
     */
    @Column(name = "remarks")
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
        addSettedField("remarks");
    }

    /**
     * 删除标识 0-否 1-是
     * @return delFlag
     */
    @Column(name = "del_flag")
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 删除标识 0-否 1-是
     * @param delFlag
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
        addSettedField("delFlag");
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