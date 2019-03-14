package org.openokr.task.service;

import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.task.request.TaskSearchVO;
import org.openokr.task.vo.*;

import java.util.List;


/**
 * Created by linaer on 2019/03/14.
 */
public interface ICustomerManageService {

    /**
     * 查询客户信息列表
     * @param customerVO
     * @return
     * @throws BusinessException
     */
    List<ApportionSelectVO> getCutomerInfoList(CustomerVO customerVO) throws BusinessException;
}