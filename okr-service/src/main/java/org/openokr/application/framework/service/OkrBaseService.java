package org.openokr.application.framework.service;


import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.LogEntity;
import org.openokr.manage.vo.LogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class OkrBaseService extends BaseServiceImpl implements IOkrBaseService {


	@Override
	public void saveOkrLog(LogVO logVO) throws BusinessException {
		LogEntity logEntity = new LogEntity();
		BeanUtils.copyProperties(logVO, logEntity);
		this.insert(logEntity);
	}

}
