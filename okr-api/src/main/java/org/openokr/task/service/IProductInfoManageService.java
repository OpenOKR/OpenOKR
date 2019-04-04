package org.openokr.task.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.vo.ApportionSelectVO;
import org.openokr.task.vo.CustomerVO;
import org.openokr.task.vo.ProductInfoVO;

import java.util.List;


/**
 * Created by linaer on 2019/03/14.
 */
public interface IProductInfoManageService {

    /**
     * 查询产品信息列表
     * @param productInfoVO
     * @return
     * @throws BusinessException
     */
    List<ApportionSelectVO> getProductInfoList(ProductInfoVO productInfoVO) throws BusinessException;
}