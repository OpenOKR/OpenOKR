package org.openokr.task.request;

import lombok.Data;
import org.openokr.common.vo.response.PageRequest;

import java.io.Serializable;

@Data
public class TeamTaskSearchVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 搜索关键词
     */
    private String searchKey;

    /**
     * 用户id
     */
    private String userId;

}