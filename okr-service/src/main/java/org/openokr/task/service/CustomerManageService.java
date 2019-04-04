package org.openokr.task.service;

import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.task.vo.ApportionSelectVO;
import org.openokr.task.vo.CustomerVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CustomerManageService extends BaseServiceImpl implements ICustomerManageService {

    private static final String MAPPER_NAMSPACE = "org.openokr.task.sqlmapper.CustomerMapper";
    private static final String CONDITION = "condition";

    @Override
    public List<ApportionSelectVO> getCutomerInfoList(CustomerVO customerVO) throws BusinessException {
        try{
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put(CONDITION,customerVO);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE+".getCutomerInfoList",paramMap);
        } catch (BusinessException e) {
            logger.error("查询客户信息列表 busi-error:{}-->[customerVO]={}", e.getMessage(), JSONUtils.objectToString(customerVO), e);
            throw e;
        } catch (Exception e) {
            logger.error("查询客户信息列表 error:{}-->[customerVO]={}", e.getMessage(),JSONUtils.objectToString(customerVO), e);
            throw new BusinessException("查询客户信息列表 失败");
        }
    }
}
