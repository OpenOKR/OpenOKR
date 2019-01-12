package org.openokr.manage.vo;

import com.zzheng.framework.base.vo.BaseVO;
import java.io.Serializable;
import java.util.Date;

public class CheckinsVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private String id;

    /**
     * 结果id
     */
    private String resultId;

    /**
     * 当前值
     */
    private String currentValue;

    /**
     * 执行状态 1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     */
    private String status;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTs;

    /**
     * 创建者
     */
    private String createUserId;

    /**
     * 评价单位（是否、百分比、数值）
     */
    private String metricUnit;

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
     * 结果id
     */
    public String getResultId() {
        return resultId;
    }

    /**
     * 结果id
     */
    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    /**
     * 当前值
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * 当前值
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * 执行状态 1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     */
    public String getStatus() {
        return status;
    }

    /**
     * 执行状态 1-正常执行，2-执行有风险，3-暂停执行，4-提前终止，5-完成
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 评价单位（是否、百分比、数值）
     */
    public String getMetricUnit() {
        return metricUnit;
    }

    /**
     * 评价单位（是否、百分比、数值）
     */
    public void setMetricUnit(String metricUnit) {
        this.metricUnit = metricUnit;
    }

}