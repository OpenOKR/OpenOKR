package org.openokr.task.service;

import com.zzheng.framework.base.utils.JSONUtils;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.manage.service.IOkrObjectService;
import org.openokr.sys.service.IUserService;
import org.openokr.task.entity.*;
import org.openokr.task.request.TaskSearchVO;
import org.openokr.task.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.openokr.common.constant.TaskConstant.CURRENT_PAGE;
import static org.openokr.common.constant.TaskConstant.PAGE_SIZE;

/**
 * Created by linaer on 2019/03/04.
 */
@Service
@Transactional
public class TaskManageService extends BaseServiceImpl implements ITaskManageService {

    private static final String MAPPER_NAMSPACE = "org.openokr.task.sqlmapper.TaskManageMapper";
    private static final String MAPPER_NAMSPACE_APPORTION = "org.openokr.task.sqlmapper.TaskApportionMapper";


    @Autowired
    IUserService userService;

    @Autowired
    IOkrObjectService okrObjectService;

    @Override
    public Page getTakListByPage(Page page, TaskSearchVO taskSearchVO) throws BusinessException{
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
                TaskKrRelEntityCondition condition = new TaskKrRelEntityCondition();
                for(TaskVO vo:taskVOS){
                    condition.clear();
                    condition.createCriteria().andTaskIdEqualTo(vo.getId());
                    Long countKR = this.countByCondition(condition);
                    vo.setCount(countKR.intValue());
                }
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
    public TaskVO saveTaskInfo(TaskSaveVO taskSaveVO) throws BusinessException {
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
            taskApportionEntity.setTaskId(entity.getId());
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
    public void delTaskInfoById(String taskId) throws BusinessException {
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
    public TaskDetailVO getTaskDetailById(String taskId) throws BusinessException {
        TaskDetailVO taskDetailVO = new TaskDetailVO();
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
            taskDetailVO.setTaskVO(taskVO);
            //3:获取分摊信息
            taskDetailVO.setApportionVOS(getTaskApportionInfo(taskId));
            //4:获取参与人员信息
            taskDetailVO.setUserInfoVOS(userService.getTaskUserInfoList(taskId));
            //5:获取关联KR信息
            //个人
            taskDetailVO.setPersonKeys(okrObjectService.getTaskObjectList(taskId,"1"));
            //团队
            taskDetailVO.setTeamKeys(okrObjectService.getTaskObjectList(taskId,"2"));
            //企业
            taskDetailVO.setCompanyKeys(okrObjectService.getTaskObjectList(taskId,"3"));
        } catch (BusinessException e) {
            logger.error("根据ID获取任务详情 busi-error:{}-->[taskId]={}", e.getMessage(),taskId, e);
            throw e;
        } catch (Exception e) {
            logger.error("根据ID获取任务详情 error:{}-->[id]={}", e.getMessage(),taskId, e);
            throw new BusinessException("根据ID获取任务详情 失败");
        }
        return taskDetailVO;
    }

    @Override
    public List<MyTaskCountInfoVO> getMyTaskCountInfo(String userId) throws BusinessException {
        List<MyTaskCountInfoVO> myTaskCountInfoVOS = new ArrayList<>();
        try{
            //1:参数校验
            if(StringUtils.isBlank(userId)){
                throw new BusinessException("用户ID为空，请确认!");
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("reportUserId",userId);
            List<String> projectIds = this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE+".getMyRenectReportProjectIIds",paramMap);
            if(projectIds==null && projectIds.size() >0){
                MyTaskCountInfoVO myTaskCountInfoVO;
                Map<String,Object> queryCountMap = new HashMap<>();
                for(String projectId:projectIds){
                    ProductInfoEntity productInfoEntity = this.selectByPrimaryKey(ProductInfoEntity.class,projectId);
                    if(productInfoEntity == null){
                        logger.error("获取不到项目{}的信息",projectId);
                        continue;
                    }
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMddHHmmss");
                    //获取当前月第一天：
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.MONTH, 0);
                    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
                    String firstDateStr = format.format(c.getTime()) + "000000";
                    Date firstDate = formatTime.parse(firstDateStr);
                    //获取前月的最后一天
                    Calendar cale = Calendar.getInstance();
                    cale.set(Calendar.DAY_OF_MONTH,0);//设置为1,当前日期既为本月最后一天
                    String lastDayStr = format.format(cale.getTime()) + "235959";
                    Date lastDate = formatTime.parse(lastDayStr);
                    queryCountMap.put("reportUserId",userId);
                    queryCountMap.put("projectId",projectId);
                    queryCountMap.put("reportStartDate", firstDate);
                    queryCountMap.put("reportEndDate",lastDate);
                    myTaskCountInfoVO = this.getMyBatisDao().selectOneBySql(MAPPER_NAMSPACE+".getProjectRelTaskCountInfo",paramMap);
                    myTaskCountInfoVOS.add(myTaskCountInfoVO);
                }
            }
        } catch (BusinessException e) {
            logger.error("获取首页我的报工统计信息 busi-error:{}-->[userId]={}", e.getMessage(),userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("获取首页我的报工统计信息 error:{}-->[userId]={}", e.getMessage(),userId, e);
            throw new BusinessException("获取首页我的报工统计信息 失败");
        }
        return myTaskCountInfoVOS;
    }

    @Override
    public List<MyTaskCountInfoVO> getMyManageTaskCountInfo(String userId) throws BusinessException {
        List<MyTaskCountInfoVO> myTaskCountInfoVOS = new ArrayList<>();
        try{
            //1:参数校验
            if(StringUtils.isBlank(userId)){
                throw new BusinessException("用户ID为空，请确认!");
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("confirmUserId",userId);
            List<String> projectIds = this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE+".getMyRenectReportProjectIIds",paramMap);
            if(projectIds==null && projectIds.size() >0){
                MyTaskCountInfoVO myTaskCountInfoVO;
                Map<String,Object> queryCountMap = new HashMap<>();
                for(String projectId:projectIds){
                    ProductInfoEntity productInfoEntity = this.selectByPrimaryKey(ProductInfoEntity.class,projectId);
                    if(productInfoEntity == null){
                        logger.error("获取不到项目{}的信息",projectId);
                        continue;
                    }
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat formatTime = new SimpleDateFormat("yyyyMMddHHmmss");
                    //获取当前月第一天：
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.MONTH, 0);
                    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
                    String firstDateStr = format.format(c.getTime()) + "000000";
                    Date firstDate = formatTime.parse(firstDateStr);
                    //获取前月的最后一天
                    Calendar cale = Calendar.getInstance();
                    cale.set(Calendar.DAY_OF_MONTH,0);//设置为1,当前日期既为本月最后一天
                    String lastDayStr = format.format(cale.getTime()) + "235959";
                    Date lastDate = formatTime.parse(lastDayStr);
                    queryCountMap.put("projectId",projectId);
                    queryCountMap.put("reportStartDate", firstDate);
                    queryCountMap.put("reportEndDate",lastDate);
                    //获取关联任务数及已报工时
                    myTaskCountInfoVO = this.getMyBatisDao().selectOneBySql(MAPPER_NAMSPACE+".getProjectRelTaskCountInfo",paramMap);
                    if(myTaskCountInfoVO != null){
                        //获取项目关联成员数
                        Integer meemberNum = this.getMyBatisDao().selectOneBySql(MAPPER_NAMSPACE+".countProjectRelUserNum",paramMap);
                        myTaskCountInfoVO.setMemberNum(meemberNum);
                        myTaskCountInfoVOS.add(myTaskCountInfoVO);
                    }

                }
            }
        } catch (BusinessException e) {
            logger.error("获取首页我管理的报工统计信息 busi-error:{}-->[userId]={}", e.getMessage(),userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("获取首页我管理的报工统计信息 error:{}-->[userId]={}", e.getMessage(),userId, e);
            throw new BusinessException("获取首页我管理的报工统计信息 失败");
        }
        return myTaskCountInfoVOS;
    }

    @Override
    public List<TaskApportionVO> getTaskApportionInfo(String taskId) throws BusinessException {
        List<TaskApportionVO> taskApportionVOS;
        try{
            //1:参数校验
            if(StringUtils.isBlank(taskId)){
                throw new BusinessException("任务ID为空，请确认!");
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("taskId",taskId);
            return this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE_APPORTION+".getTaskApportionInfo",paramMap);
        } catch (BusinessException e) {
            logger.error("获取任务分摊信息 busi-error:{}-->[userId]={}", e.getMessage(),taskId, e);
            throw e;
        } catch (Exception e) {
            logger.error("获取任务分摊信息 error:{}-->[userId]={}", e.getMessage(),taskId, e);
            throw new BusinessException("获取任务分摊信息 失败");
        }
    }

    @Override
    public List<DailyVO> getMyRecentTaskInfo(String userId) throws BusinessException {
        List<DailyVO> myDailyVOS;
        try{
            //1:参数校验
            if(StringUtils.isBlank(userId)){
                throw new BusinessException("用户ID为空，请确认!");
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("reportUserId",userId);
            myDailyVOS = this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE+".getMyRecentTaskInfo",paramMap);
        } catch (BusinessException e) {
            logger.error("获取首页我的近期报工 busi-error:{}-->[userId]={}", e.getMessage(),userId, e);
            throw e;
        } catch (Exception e) {
            logger.error("获取首页我的近期报工 error:{}-->[userId]={}", e.getMessage(),userId, e);
            throw new BusinessException("获取首页我的近期报工 失败");
        }
        return myDailyVOS;
    }

    @Override
    public Page getTakListByUser(Page page, TaskSearchVO taskSearchVO) throws BusinessException {
        try {
            if (taskSearchVO==null){
                throw new BusinessException("查询参数为空");
            }
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("vo",taskSearchVO);
            if (page!=null){
                paramMap.put("page",page);
            }else {
                //page如果为空就不分页查询
                paramMap.put("page",null);
                page = new Page();
            }
            Integer count = this.getMyBatisDao().selectOneBySql(MAPPER_NAMSPACE+".countTaskListByUserData",paramMap);
            page.setTotalRecord(count);
            if(count>0){
                List<TaskVO> taskVOS = this.getMyBatisDao().selectListBySql(MAPPER_NAMSPACE+".getTaskListByUserData",paramMap);
                page.setRecords(taskVOS);
            }
        } catch (BusinessException e) {
            logger.error("根据当前用户分页查询任务列表信息 busi-error:{}-->[page]={},[taskSearchVO]={}", e.getMessage(), JSONUtils.objectToString(page),JSONUtils.objectToString(taskSearchVO), e);
            throw e;
        } catch (Exception e) {
            logger.error("根据当前用户分页查询任务列表信息 error:{}-->[page]={},[taskSearchVO]={}", e.getMessage(), JSONUtils.objectToString(page),JSONUtils.objectToString(taskSearchVO), e);
            throw new BusinessException("根据当前用户分页查询任务列表信息 失败");
        }
        return page;
    }

}