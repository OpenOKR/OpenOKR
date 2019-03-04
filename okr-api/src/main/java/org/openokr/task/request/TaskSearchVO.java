package org.openokr.task.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.openokr.common.vo.response.PageRequest;

import java.io.Serializable;
import java.util.Date;

@Data
public class TaskSearchVO extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关键词")
    private String searchKey;

    @ApiModelProperty(value = "查询开始日期")
    private Date queryStartDate;

    @ApiModelProperty(value = "查询结束日期")
    private Date queryEndDate;

}