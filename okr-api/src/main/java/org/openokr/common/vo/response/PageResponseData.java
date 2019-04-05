package org.openokr.common.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口分页
 *
 * @Author fyh
 * @Date 2018/08/16
 */
@Data
public class PageResponseData<T> implements Serializable {
    private static final long serialVersionUID = -4313766497412557907L;
    @ApiModelProperty(value = "每页数量",example = "10")
    private int pageSize = 10;
    @ApiModelProperty(value = "总数量",example = "0")
    private int totalRecord = 0;
    @ApiModelProperty(value = "当前页码",example = "1")
    private int currentPage = 1;
    @ApiModelProperty(value = "总页数",example = "0")
    private int totalPage;
    @ApiModelProperty(value = "业务数据")
    private T data;
}
