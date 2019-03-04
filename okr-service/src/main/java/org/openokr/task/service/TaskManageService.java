package org.openokr.task.service;

import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.task.entity.*;
import org.openokr.task.request.TaskSearchVO;
import org.openokr.task.vo.TaskApportionVO;
import org.openokr.task.vo.TaskSaveVO;
import org.openokr.task.vo.TaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.openokr.common.constant.AcTaskConstant.CURRENT_PAGE;
import static org.openokr.common.constant.AcTaskConstant.PAGE_SIZE;

/**
 * Created by linaer on 2019/03/04.
 */
@Service
@Transactional
public class TaskManageService extends BaseServiceImpl implements ITaskManageService {

    private static final String MAPPER_NAMSPACE = "org.openokr.task.sqlmapper.TaskManageMapper";

    @Override
    public Page getTakListByPage(Page page, TaskSearchVO taskSearchVO) throws Exception{
        try{
            if(page == null){
                page = new Page(CURRENT_PAGE,PAGE_SIZE);
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("page",page);
            paramMap.put("vo",taskSearchVO);
            Integer count = this.getMyBatisDao().selectOneBySql(MAPPER_NAMSPACE+".countTableData",paramMap);
            page.setTotalRecord(count);
            if(count > 0){
                List<TaskVO> taskVOS = this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE+".getTableData",paramMap);
                page.setRecords(taskVOS);
            }
        } catch (BusinessException e) {
            logger.error("分页查询任务列表信息 busi-error:{}-->[page]={},[taskSearchVO]={}", e.getMessage(), JSONUtils.objectToString(page),JSONUtils.objectToString(taskSearchVO), e);
            throw e;
        } catch (Exception e) {
            logger.error("分页查询任务列表信息 error:{}-->[page]={},[taskSearchVO]={}", e.getMessage(), JSONUtils.objectToString(page),JSONUtils.objectToString(taskSearchVO), e);
            throw new BusinessException("分页查询任务列表信息 失败");
        }
        return page;
    }

    @Override
    public TaskVO saveTaskInfo(TaskSaveVO taskSaveVO) throws Exception {
        TaskVO taskVO;
        try{
            //1:参数校验
            if(taskSaveVO == null || taskSaveVO.getTaskVO() == null){
                throw new BusinessException("新增参数为空，请确认!");
            }
            if(StringUtils.isBlank(taskSaveVO.getTaskVO().getTaskName())){
                throw new BusinessException("任务名称为空，请确认!");
            }
            if(taskSaveVO.getApportionVOS() == null || taskSaveVO.getApportionVOS().size() < 1){
                throw new BusinessException("任务分摊信息为空，请确认!");
            }
            Date date = new Date();
            taskVO = taskSaveVO.getTaskVO();
            List<TaskApportionVO> apportionVOS = taskSaveVO.getApportionVOS();
            List<String> userIds = taskSaveVO.getUserIds();
            List<String> krIds = taskSaveVO.getKrIds();
            if(StringUtils.isBlank(taskVO.getId())){
                //执行新增操作
                //2:保存任务主表信息
                TaskEntity entity = new TaskEntity();
                BeanUtils.copyProperties(taskVO,entity);
                entity.setCreateTs(date);
                entity.setUpdateTs(date);
                this.insert(entity);
                taskVO.setId(entity.getId());
                //3:保存分摊信息、参与人员信息、关联KR信息
                saveTaskOtherInfo(apportionVOS, userIds, krIds, entity);
            }else{
                //执行更新操作
                //1:获取旧的任务信息
                TaskEntity oldTaskEntity = this.selectByPrimaryKey(TaskEntity.class,taskVO.getId());
                if(oldTaskEntity==null){
                    throw new BusinessException("更新的任务信息已不存在，请确认!");
                }
                TaskEntity entity = new TaskEntity();
                BeanUtils.copyProperties(taskVO,entity);
                entity.setUpdateTs(date);
                this.update(entity);
                //2:删除旧的分摊信息、参与人员信息、关联KR信息
                delTaskOtherInfo(entity.getId());
                //3:重新保存分摊信息、参与人员信息、关联KR信息
                saveTaskOtherInfo(apportionVOS, userIds, krIds, entity);
            }
        } catch (BusinessException e) {
            logger.error("保存任务信息 busi-error:{}-->[taskSaveVO]={}", e.getMessage(),JSONUtils.objectToString(taskSaveVO), e);
            throw e;
        } catch (Exception e) {
            logger.error("保存任务信息 error:{}-->[taskSaveVO]={}", e.getMessage(),JSONUtils.objectToString(taskSaveVO), e);
            throw new BusinessException("保存任务信息 失败");
        }
        return taskVO;
    }

    private void delTaskOtherInfo(String id) {
        //1删除分摊信息
        TaskApportionEntityCondition taskApportionEntityCondition = new TaskApportionEntityCondition();
        taskApportionEntityCondition.createCriteria().andTaskIdEqualTo(id);
        this.deleteByCondition(taskApportionEntityCondition);
        //2删除参与人员信息
        TaskUserRelEntityCondition taskUserRelEntityCondition = new TaskUserRelEntityCondition();
        taskUserRelEntityCondition.createCriteria().andTaskIdEqualTo(id);
        this.deleteByCondition(taskUserRelEntityCondition);
        //3删除关联KR信息
        TaskKrRelEntityCondition taskKrRelEntityCondition = new TaskKrRelEntityCondition();
        taskKrRelEntityCondition.createCriteria().andTaskIdEqualTo(id);
        this.deleteByCondition(taskKrRelEntityCondition);
    }

    private void saveTaskOtherInfo(List<TaskApportionVO> apportionVOS, List<String> userIds, List<String> krIds, TaskEntity entity) {
        //1:保存分摊信息
        TaskApportionEntity taskApportionEntity;
        for(TaskApportionVO apportionVO:apportionVOS){
            taskApportionEntity = new TaskApportionEntity();
            BeanUtils.copyProperties(apportionVO,taskApportionEntity);
            apportionVO.setTaskId(entity.getId());
            this.insert(taskApportionEntity);
        }
        //2:保存参与人员信息
        if(userIds!=null && userIds.size()>0){
            TaskUserRelEntity taskUserRelEntity;
            for(String userId:userIds){
                taskUserRelEntity = new TaskUserRelEntity();
                taskUserRelEntity.setTaskId(entity.getId());
                taskUserRelEntity.setUserId(userId);
                this.insert(taskUserRelEntity);
            }
        }
        //3:保存kr关系信息
        if(krIds!=null && krIds.size()>0){
            TaskKrRelEntity taskKrRelEntity;
            for(String krId:krIds){
                taskKrRelEntity = new TaskKrRelEntity();
                taskKrRelEntity.setTaskId(entity.getId());
                taskKrRelEntity.setKrId(krId);
                this.insert(taskKrRelEntity);
            }
        }
    }

    @Override
    public void delTaskInfoById(String taskId) throws Exception {
        try{
            //1:参数校验
            if(StringUtils.isBlank(taskId)){
                throw new BusinessException("任务ID为空，请确认!");
            }
            //2:删除任务主表信息
            this.deleteByPrimaryKey(TaskEntity.class,taskId);
            //3:删除分摊信息、参与人员信息、关联KR信息
            this.delTaskOtherInfo(taskId);
        } catch (BusinessException e) {
            logger.error("根据ID删除任务信息 busi-error:{}-->[taskId]={}", e.getMessage(),taskId, e);
            throw e;
        } catch (Exception e) {
            logger.error("根据ID删除任务信息 error:{}-->[id]={}", e.getMessage(),taskId, e);
            throw new BusinessException("根据ID删除任务信息 失败");
        }
    }

    @Override
    public TaskSaveVO getTaskDetailById(String taskId) throws Exception {
        TaskSaveVO taskSaveVO = new TaskSaveVO();
        try{
            //1:参数校验
            if(StringUtils.isBlank(taskId)){
                throw new BusinessException("任务ID为空，请确认!");
            }
            TaskVO taskVO = new TaskVO();
            //2:获取主表任务主表信息
            TaskEntity taskEntity = this.selectByPrimaryKey(TaskEntity.class,taskId);
            if(taskEntity == null){
                throw new BusinessException("获取不到任务详情!");
            }
            BeanUtils.copyProperties(taskEntity,taskVO);
            taskSaveVO.setTaskVO(taskVO);
            //3:获取分摊信息
            TaskApportionEntityCondition taskApportionEntityCondition = new TaskApportionEntityCondition();
            taskApportionEntityCondition.createCriteria().andTaskIdEqualTo(taskId);
            List<TaskApportionEntity> taskApportionEntities = this.selectByCondition(taskApportionEntityCondition);
            if(taskApportionEntities!=null && taskApportionEntities.size() >0){
                List<TaskApportionVO> taskApportionVOS = JSONUtils.objectToList(taskApportionEntities,TaskApportionVO.class);
                taskSaveVO.setApportionVOS(taskApportionVOS);
            }
            //4:获取参与人员信息
            TaskUserRelEntityCondition taskUserRelEntityCondition = new TaskUserRelEntityCondition();
            taskUserRelEntityCondition.createCriteria().andTaskIdEqualTo(taskId);
            List<TaskUserRelEntity> taskUserRelEntities = this.selectByCondition(taskUserRelEntityCondition);
            if(taskUserRelEntities!=null && taskUserRelEntities.size() >0){
                List<String> userIds = new ArrayList<>();
                for(TaskUserRelEntity entity:taskUserRelEntities){
                    userIds.add(entity.getUserId());
                }
                taskSaveVO.setUserIds(userIds);
            }
            //5:获取关联KR信息
            this.delTaskOtherInfo(taskId);
            TaskKrRelEntityCondition taskKrRelEntityCondition = new TaskKrRelEntityCondition();
            taskKrRelEntityCondition.createCriteria().andTaskIdEqualTo(taskId);
            List<TaskKrRelEntity> taskKrRelEntities = this.selectByCondition(taskKrRelEntityCondition);
            if(taskKrRelEntities!=null && taskKrRelEntities.size() >0){
                List<String> krIds = new ArrayList<>();
                for(TaskKrRelEntity entity:taskKrRelEntities){
                    krIds.add(entity.getKrId());
                }
                taskSaveVO.setKrIds(krIds);
            }
        } catch (BusinessException e) {
            logger.error("根据ID获取任务详情 busi-error:{}-->[taskId]={}", e.getMessage(),taskId, e);
            throw e;
        } catch (Exception e) {
            logger.error("根据ID获取任务详情 error:{}-->[id]={}", e.getMessage(),taskId, e);
            throw new BusinessException("根据ID获取任务详情 失败");
        }
        return taskSaveVO;
    }
}