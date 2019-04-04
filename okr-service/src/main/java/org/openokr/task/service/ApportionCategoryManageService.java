package org.openokr.task.service;

import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.openokr.common.constant.TaskConstant;
import org.openokr.task.entity.ApportionCategoryEntity;
import org.openokr.task.entity.ApportionCategoryEntityCondition;
import org.openokr.task.vo.ApportionCategoryVO;
import org.openokr.task.vo.ApportionSelectVO;
import org.openokr.task.vo.CustomerVO;
import org.openokr.task.vo.ProductInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linaer
 * @create 2019/3/14
 */
@Service
@Transactional
public class ApportionCategoryManageService extends BaseServiceImpl implements IApportionCategoryManageService {

    private static final String CONDITION = "condition";

    @Autowired
    ICustomerManageService customerManageService;

    @Autowired
    IProductInfoManageService productInfoManageService;

    @Override
    public List<ApportionSelectVO> getApportionSelectList(String categoryId) throws BusinessException {
        try{
            ProductInfoVO productInfoVO = new ProductInfoVO();
            productInfoVO.setCategoryId(categoryId);
            return productInfoManageService.getProductInfoList(productInfoVO);
        } catch (BusinessException e) {
            logger.error("获取分摊下拉选择信息列表 busi-error:{}-->[categoryId]={}", e.getMessage(), categoryId, e);
            throw e;
        } catch (Exception e) {
            logger.error("获取分摊下拉选择信息列表 error:{}-->[categoryId]={}", e.getMessage(),categoryId, e);
            throw new BusinessException("获取分摊下拉选择信息列表 失败");
        }
    }

    @Override
    public List<ApportionCategoryVO> getApportionCategoryList() throws BusinessException {
        try{
            ApportionCategoryEntityCondition condition = new ApportionCategoryEntityCondition();
            condition.createCriteria();
            List<ApportionCategoryEntity> apportionCategoryEntities = this.selectByCondition(condition);
            return JSONUtils.objectToList(apportionCategoryEntities,ApportionCategoryVO.class);
        } catch (BusinessException e) {
            logger.error("获取分摊类别信息列表 busi-error:{}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("获取分摊类别信息列表 error:{}", e.getMessage(), e);
            throw new BusinessException("获取分摊类别信息列表 失败");
        }
    }
}
