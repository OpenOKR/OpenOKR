package org.openokr.manage.service;

import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.base.utils.BeanUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.openokr.manage.entity.MessagesEntity;
import org.openokr.manage.vo.MessagesVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OkrMessageService extends BaseServiceImpl implements IOkrMessageService {

    private final static String MAPPER_NAMESPACE = "org.openokr.manage.sqlmapper.OkrMessageMapper";

    @Override
    public List<MessagesVO> getMessageList(Page page, String userId, Integer limitAmount) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("limitAmount", limitAmount);
        params.put("page", page);
        List<MessagesVO> messageList = this.getDao().selectListBySql(MAPPER_NAMESPACE + ".getMessageList", params);

        return  messageList;
    }

    @Override
    public Page getMessageByPage(Page page, String userId) throws BusinessException {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("page", page);
        // 查询总数
        int count = this.getMyBatisDao().selectOneBySql(MAPPER_NAMESPACE + ".countMessageList", params);
        page.setTotalRecord(count);
        // 查询分页
        List<MessagesVO> messageList = this.getMessageList(page, userId, null);
        page.setRecords(messageList);

        return  page;
    }

    @Override
    public ResponseResult update(MessagesVO vo, String currentUserId) {
        MessagesEntity entity = this.getMyBatisDao().selectByPrimaryKey(MessagesEntity.class, vo.getId());
        BeanUtils.copyBean(vo, entity);
        entity.setUpdateUserId(currentUserId);
        entity.setUpdateTs(new Date());
        this.getMyBatisDao().update(entity);
        return new ResponseResult(true, null, "更新成功！");
    }

    @Override
    public MessagesVO getById(String id) {
        MessagesEntity entity = this.getMyBatisDao().selectByPrimaryKey(MessagesEntity.class, id);
        MessagesVO message = BeanUtils.copyToNewBean(entity, MessagesVO.class);
        return message;
    }

    public ResponseResult dealMessage(String messageId, String userId) throws BusinessException {
        ResponseResult result = new ResponseResult();
        try {
            MessagesEntity messagesEntity = this.selectByPrimaryKey(MessagesEntity.class, messageId);
            if (messagesEntity != null) {
                messagesEntity.setIsProcessed("1");//已处理
                messagesEntity.setIsRead("1");//已读
                messagesEntity.setUpdateTs(new Date());
                messagesEntity.setUpdateUserId(userId);
            }
        } catch (Exception e) {
            logger.error("消息处理发生异常", e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

}