package org.openokr.common.vo.response;

import com.zzheng.framework.base.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @Author fyh
 * @Date 2018/08/22
 */
public class PageRequest extends BaseVO implements Serializable {

    @ApiModelProperty(value = "页码")
    private Integer currentPage = 1;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}