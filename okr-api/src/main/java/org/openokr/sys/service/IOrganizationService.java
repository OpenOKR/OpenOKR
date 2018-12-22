package org.openokr.sys.service;

import org.openokr.sys.vo.OrganizationVOExt;

import java.util.List;

/**
 * Created by zhengzheng on 2018/12/21.
 */
public interface IOrganizationService {

    List<OrganizationVOExt> findAll();

}