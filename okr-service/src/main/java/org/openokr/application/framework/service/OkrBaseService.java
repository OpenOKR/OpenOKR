package org.openokr.application.framework.service;

import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.LogEntity;
import org.openokr.manage.entity.MessagesEntity;
import org.openokr.manage.entity.MessagesEntityCondition;
import org.openokr.manage.entity.ResultsEntity;
import org.openokr.manage.entity.ResultsEntityCondition;
import org.openokr.manage.enumerate.MessageTypeEnum;
import org.openokr.manage.service.IOkrMessageService;
import org.openokr.manage.service.IOkrTimeSessionsService;
import org.openokr.manage.vo.LogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public void deleteAuditMsg(String objectId) {
		MessagesEntityCondition condition = new MessagesEntityCondition();
		// 目标审核消息
		condition.createCriteria().andTypeEqualTo(MessageTypeEnum.TYPE_2.getCode())
				.andTargetIdEqualTo(objectId).andIsProcessedEqualTo("0");

		MessagesEntity messagesEntity = new MessagesEntity();
		messagesEntity.setDelFlag("1");
		messagesEntity.setRemarks("目标重新审核，删除旧数据");

		this.updateByCondition(messagesEntity, condition);
		// 协同人审核消息
		ResultsEntityCondition resultsEntityCondition = new ResultsEntityCondition();
		resultsEntityCondition.createCriteria().andObjectIdEqualTo(objectId);
		List<ResultsEntity> resultsEntityList = this.selectByCondition(resultsEntityCondition);
		List<String> krIds = new ArrayList<>();
		for (ResultsEntity resultsEntity : resultsEntityList) {
			krIds.add(resultsEntity.getId());
		}
		condition = new MessagesEntityCondition();
		condition.createCriteria().andTypeEqualTo(MessageTypeEnum.TYPE_3.getCode())
				.andTargetIdIn(krIds).andIsProcessedEqualTo("0");

		messagesEntity.setRemarks("协同人重新确认，删除旧数据");

		this.updateByCondition(messagesEntity, condition);
	}
}