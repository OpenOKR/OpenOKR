package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class ObjectLabelRelaVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 目标id
     */
    private String objectId;

    /**
     * 标签id
     */
    private String labelId;

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
     * 目标id
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * 目标id
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * 标签id
     */
    public String getLabelId() {
        return labelId;
    }

    /**
     * 标签id
     */
    public void setLabelId(String labelId) {
        this.labelId = labelId;
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