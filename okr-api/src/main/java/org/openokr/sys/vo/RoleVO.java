package org.openokr.sys.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class RoleVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建日期
     */
    private Date createTs;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 更新日期
     */
    private Date updateTs;

    /**
     * 更新人id
     */
    private String updateUserId;



    /**
     * 
     */
    public String getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 创建日期
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 创建日期
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 创建人id
     */
    public String getCreateUserId() {
        return createUserId;
    }

    /**
     * 创建人id
     */
    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 更新日期
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 更新日期
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }

    /**
     * 更新人id
     */
    public String getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 更新人id
     */
    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

}