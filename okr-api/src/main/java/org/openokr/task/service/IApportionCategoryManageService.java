package org.openokr.task.service;

import com.zzheng.framework.exception.BusinessException;
import org.openokr.task.vo.ApportionCategoryVO;
import org.openokr.task.vo.ApportionSelectVO;
import org.openokr.task.vo.CustomerVO;

import java.util.List;


/**
 * Created by linaer on 2019/03/14.
 */
public interface IApportionCategoryManageService {

    /**
     * 获取分摊下拉选择信息列表
     * @param categoryId
     * @return
     * @throws BusinessException
     */
    List<ApportionSelectVO> getApportionSelectList(String categoryId) throws BusinessException;

    /**
     * 获取分摊类别信息列表
     * @return
     * @throws BusinessException
     */
    List<ApportionCategoryVO> getApportionCategoryList() throws BusinessException;
}