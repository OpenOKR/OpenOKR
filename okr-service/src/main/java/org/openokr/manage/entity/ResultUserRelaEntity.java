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
@Table(name = "t_okr_manage_result_user_rela")
public class ResultUserRelaEntity extends BaseEntity implements Serializable {
    /** 关键结果id */
    private String resultId;

    /** 用户id */
    private String userId;

    /** 状态 1.待确认 2.确认 3.拒绝 */
    private String status;

    /** 创建时间 */
    private Date createTs;

    /** 创建者 */
    private String createUserId;

    private static final long serialVersionUID = 1L;

    /**
     * 关键结果id
     * @return resultId
     */
    @Column(name = "result_id")
    public String getResultId() {
        return resultId;
    }

    /**
     * 关键结果id
     * @param resultId
     */
    public void setResultId(String resultId) {
        this.resultId = resultId;
        addSettedField("resultId");
    }

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
     * 状态 1.待确认 2.确认 3.拒绝
     * @return status
     */
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    /**
     * 状态 1.待确认 2.确认 3.拒绝
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
        addSettedField("status");
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
        return ResultUserRelaEntityMapper.class;
    }
}