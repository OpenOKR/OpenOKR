package org.openokr.manage.service;


import com.zzheng.framework.adapter.vo.ResponseResult;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import org.openokr.manage.vo.MessagesVO;

import java.util.List;

/**
 * 消息管理service
 * Created by hjh on 2018/12/21.
 */
public interface IOkrMessageService {

    /**
     * 获取消息列表
     * @param page (page和limitAmount只要传一个就可以)
     * @param userId
     * @param limitAmount 查询消息条数
     * @return
     * @throws BusinessException
     */
    List<MessagesVO> getMessageList(Page page, String userId, Integer limitAmount) throws BusinessException;

    /**
     * 分页获取消息列表
     * @param page
     * @param userId
     * @return
     * @throws BusinessException
     */
    Page getMessageByPage(Page page, String userId) throws BusinessException;

    /**
     * 更新消息
     * @param vo
     * @param currentUserId
     */
    ResponseResult update(MessagesVO vo, String currentUserId);

    /**
     * 根据id获取消息
     * @param id
     * @return
     */
    MessagesVO getById(String id);
}