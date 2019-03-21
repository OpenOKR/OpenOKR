package org.openokr.sys.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.base.utils.MapUtils;
import com.zzheng.framework.base.utils.StringUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.sys.entity.OrganizationEntity;
import org.openokr.sys.entity.OrganizationEntityCondition;
import org.openokr.sys.vo.OrganizationVOExt;
import org.openokr.sys.vo.request.TreeDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzheng on 2018/12/21.
 */
@Service
@Transactional
public class OrganizationService extends BaseServiceImpl implements IOrganizationService {

    private static final String MAPPER_NAMESPACE = "org.openokr.sys.sqlmapper.OrganizationMapper";

    @Autowired
    private IUserService userService;

    @Override
    public List<OrganizationVOExt> findAll() {
        String sql = "SELECT t.id,t.parent_id AS \"parentId\",t2.name AS \"parentName\",t.description,t.name,t.code" +
                " FROM t_okr_sys_organization t, t_okr_sys_organization t2 WHERE t.parent_id = t2.id" +
                " UNION SELECT t3.id,t3.parent_id AS \"parentId\",'' AS \"parentName\",t3.description,t3.name,t3.code" +
                " FROM t_okr_sys_organization t3 WHERE t3.id='1'";
        List<Map<String, Object>> maps = this.getDao().selectListByDynamicSql(sql);
        return MapUtils.mapListToBeanList(maps, OrganizationVOExt.class);
    }

    @Override
    public ResponseResult addOrModify(OrganizationVOExt organization) {
        OrganizationEntity saveEntity = null;
        if (StringUtils.isEmpty(organization.getParentId())) {
            return new ResponseResult("保存失败，上级机构不能为空", false);
        }
        if (countByName(organization.getId(), organization.getName()) > 0) {
            return new ResponseResult("保存失败，" + organization.getName() + " 已经存在", false);
        }
        if (countByCode(organization.getId(), organization.getCode()) > 0) {
            return new ResponseResult("保存失败，" + organization.getCode() + " 已经存在", false);
        }
        if (organization.getId() == null) {//新增
            //保存
            OrganizationEntity entity = BeanUtils.copyToNewBean(organization, OrganizationEntity.class);
            saveEntity = this.saveWithQuery(entity);
        } else if (organization.getId() != null) {//修改
            if (organization.getId().equals(organization.getParentId())) {
                return new ResponseResult("保存失败，当前机构的上级不能是自己", false);
            }
            //保存
            OrganizationEntity entity = BeanUtils.copyToNewBean(organization, OrganizationEntity.class);
            saveEntity = this.saveWithQuery(entity);
        }
        //
        if (saveEntity != null) {
            return new ResponseResult("保存成功");
        } else {
            return new ResponseResult("保存失败", false);
        }
    }

    @Override
    public ResponseResult delete(String id) {
        if ("1".equals(id)) {
            return new ResponseResult("删除失败，不允许删除根机构", false);
        }
        //判断子
        if (countChildrenByParentId(id) > 0) {
            return new ResponseResult("删除失败，存在子机构关联，不允许删除", false);
        }
        //判断用户
        if (userService.countByOrganizationId(id) > 0) {
            return new ResponseResult("删除失败，还有用户归属于该机构，不允许删除", false);
        }
        if (this.deleteByPrimaryKey(OrganizationEntity.class, id) > 0) {
            return new ResponseResult("删除成功");
        } else {
            return new ResponseResult("删除失败");
        }
    }

    @Override
    public List<OrganizationVOExt> findCurrentAndChildren(String currentOrganizationId) {
        if (StringUtils.isEmpty(currentOrganizationId)) {
            return null;
        }
        String sql = "SELECT t.id,t.parent_id as parentId,t.description,t.name,t.code FROM t_pfm_ssm_organization t WHERE t.id= #{id}";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("id", currentOrganizationId);
        Map<String, Object> map = this.getDao().selectOneByDynamicSql(sql, parameterMap);
        OrganizationVOExt vo = MapUtils.mapToBean(map, OrganizationVOExt.class);
        List<OrganizationVOExt> currentAndChildrenList = new ArrayList<>();
        currentAndChildrenList.add(vo);
        //查询子数据
        findChildren(currentAndChildrenList, vo.getId());
        return currentAndChildrenList;
    }

    @Override
    public List<Map<String, Object>> findContainUserOfAll(String currentUserId) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentUserId", currentUserId);
        return this.getMyBatisDao().selectListBySql(MAPPER_NAMESPACE + ".findContainUserOfAll", params);
    }

    @Override
    public List<TreeDataVO> findOrganizationTreeData(String currentUserId) throws BusinessException {
        List<TreeDataVO> treeDataVOS = new ArrayList<>();
        try{
            TreeDataVO treeDataVO = new TreeDataVO();
            List<Map<String, Object>> mapDataList = this.findContainUserOfAll(currentUserId);
            if(mapDataList != null && !mapDataList.isEmpty()){
                for(Map<String, Object> map:mapDataList){
                    if("00000000".equals(map.get("pId"))){
                        treeDataVO.setId((String) map.get("id"));
                        treeDataVO.setLabel((String) map.get("realName"));
                        treeDataVO.setChildren(findOrganizationChildrenData(map,mapDataList));
                        treeDataVOS.add(treeDataVO);
                    }else{
                        continue;
                    }
                }
            }
        } catch (BusinessException e) {
            logger.error("获取组织机构树 busi-error:{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("获取组织机构树 error:{}-->", e.getMessage(), e);
            throw new BusinessException("获取组织机构树 失败");
        }
        return treeDataVOS;
    }

    private List<TreeDataVO> findOrganizationChildrenData(Map<String, Object> mapData,List<Map<String, Object>> mapDataList) throws BusinessException {
        List<TreeDataVO> treeDataVOS = new ArrayList<>();
        try{
            TreeDataVO treeDataVO;
            List<Map<String, Object>> childrenList = (List<Map<String,Object>>) mapData.get("children");
            if(childrenList!=null && !childrenList.isEmpty()){
                for(Map<String, Object> map:childrenList){
                    treeDataVO = new TreeDataVO();
                    treeDataVO.setId((String) map.get("id"));
                    treeDataVO.setLabel((String) map.get("realName"));
                    treeDataVO.setType("01");
                    treeDataVOS.add(treeDataVO);
                }
            }
            if(mapDataList != null && !mapDataList.isEmpty()){
                String id;
                String pId;
                for(Map<String, Object> map:mapDataList){
                    id = (String)mapData.get("id");
                    pId = (String)map.get("pId");
                    if(id.equals(pId)){
                        treeDataVO = new TreeDataVO();
                        treeDataVO.setId((String)map.get("id"));
                        treeDataVO.setLabel((String)map.get("realName"));
                        treeDataVO.setChildren(findOrganizationChildrenData(map,mapDataList));
                        treeDataVO.setType("00");
                        treeDataVOS.add(treeDataVO);
                    }else{
                        continue;
                    }
                }
            }
        } catch (BusinessException e) {
            logger.error("获取组织机构树 busi-error:{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("获取组织机构树 error:{}-->", e.getMessage(), e);
            throw new BusinessException("获取组织机构树 失败");
        }
        return treeDataVOS;
    }

    private long countByCode(String id, String code) {
        OrganizationEntityCondition condition = new OrganizationEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andCodeEqualTo(code);
        } else {
            condition.createCriteria().andCodeEqualTo(code);
        }
        return this.countByCondition(condition);
    }

    private long countByName(String id, String name) {
        OrganizationEntityCondition condition = new OrganizationEntityCondition();
        if (StringUtils.isNotBlank(id)) {
            condition.createCriteria().andIdNotEqualTo(id).andNameEqualTo(name);
        } else {
            condition.createCriteria().andNameEqualTo(name);
        }
        return this.countByCondition(condition);
    }

    private long countChildrenByParentId(String parentId) {
        OrganizationEntityCondition condition = new OrganizationEntityCondition();
        condition.createCriteria().andParentIdEqualTo(parentId);
        return this.countByCondition(condition);
    }

    /**
     * 查找子数据
     */
    private void findChildren(List<OrganizationVOExt> organizationList, String parentId) {
        String sql = "SELECT t.id,t.parent_id as \"parentId\",t.description,t.name,t.code FROM t_pfm_ssm_organization t where ( parent_id = #{id} ) ";
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("id", parentId);
        List<Map<String, Object>> map = this.getDao().selectListByDynamicSql(sql, parameterMap);
        List<OrganizationVOExt> vos = MapUtils.mapListToBeanList(map, OrganizationVOExt.class);
        organizationList.addAll(vos);
        for (OrganizationVOExt childrenVo : vos) {
            findChildren(organizationList, childrenVo.getId());
        }
    }
}