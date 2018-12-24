package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import org.openokr.sys.vo.OrganizationVOExt;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/21.
 */
public interface IOrganizationService {

    List<OrganizationVOExt> findAll();

    ResponseResult addOrModify(OrganizationVOExt vo);

    ResponseResult delete(String id);

    List<OrganizationVOExt> findCurrentAndChildren(String currentOrganizationId);
}