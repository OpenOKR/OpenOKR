package org.openokr.application.framework.service;


import com.zzheng.framework.exception.BusinessException;
import org.openokr.manage.vo.LogVO;

/**
 * 公共基础服务
 * @author HJH
 *
 */
public interface IOkrBaseService {

	/**
	 * 保存OKR操作记录
	 * @param logVO
	 * @throws BusinessException
     */
	void saveOkrLog(LogVO logVO) throws BusinessException;
	
}
