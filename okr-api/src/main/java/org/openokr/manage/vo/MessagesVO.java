package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class MessagesVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 类型 1.一般文本 2.目标审核 3.协同审核 4.目标跳转
     */
    private String type;

    /**
     * 关联id
     */
    private String targetId;

    /**
     * 是否已处理 0-否，1-是
     */
    private String isProcessed;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private Date updateTs;

    /**
     * 更新者
     */
    private String updateUserId;

    /**
     * 是否已读 0.未读 1.已读
     */
    private String isRead;



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
     * 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 类型 1.一般文本 2.目标审核 3.协同审核 4.目标跳转
     */
    public String getType() {
        return type;
    }

    /**
     * 类型 1.一般文本 2.目标审核 3.协同审核 4.目标跳转
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 关联id
     */
    public String getTargetId() {
        return targetId;
    }

    /**
     * 关联id
     */
    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * 是否已处理 0-否，1-是
     */
    public String getIsProcessed() {
        return isProcessed;
    }

    /**
     * 是否已处理 0-否，1-是
     */
    public void setIsProcessed(String isProcessed) {
        this.isProcessed = isProcessed;
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

    /**
     * 更新时间
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新时间
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 更新者
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新者
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    /**
     * 是否已读 0.未读 1.已读
     */
    public String getIsRead() {
        return isRead;
    }

    /**
     * 是否已读 0.未读 1.已读
     */
    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

}