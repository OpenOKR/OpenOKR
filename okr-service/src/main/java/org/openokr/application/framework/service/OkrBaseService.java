package org.openokr.application.framework.service;

import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.LogEntity;
import org.openokr.manage.service.IOkrTimeSessionsService;
import org.openokr.manage.vo.LogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OkrBaseService extends BaseServiceImpl implements IOkrBaseService {

	@Autowired
	private IOkrTimeSessionsService okrTimeSessionsService;

	@Override
	public void saveOkrLog(LogVO logVO) throws BusinessException {
		LogEntity logEntity = new LogEntity();
		BeanUtils.copyBean(logVO, logEntity);
		this.insert(logEntity);
	}

	/**
	 * 当前时间段ID
	 * @return
	 * @throws BusinessException
     */
	@Override
	public String getCurrentTimeSessionId() throws BusinessException {
		return okrTimeSessionsService.getDefaultTimeSession().getId();
	}
}