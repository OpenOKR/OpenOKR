package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import org.openokr.sys.vo.OrganizationVOExt;
import org.openokr.sys.vo.request.TreeDataVO;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzheng on 2018/12/21.
 */
public interface IOrganizationService {

    List<OrganizationVOExt> findAll();

    ResponseResult addOrModify(OrganizationVOExt vo);

    ResponseResult delete(String id);

    List<OrganizationVOExt> findCurrentAndChildren(String currentOrganizationId);

    List<Map<String, Object>> findContainUserOfAll(String currentUserId);

    /**
     * 获取组织机构树
     * @return
     */
    List<TreeDataVO> findOrganizationTreeData(String currentUserId) throws BusinessException;

}