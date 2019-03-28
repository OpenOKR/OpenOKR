package org.openokr.task.service;

import com.alibaba.fastjson.JSON;
import com.zzheng.framework.exception.BusinessException;
import com.zzheng.framework.mybatis.dao.pojo.Page;
import com.zzheng.framework.mybatis.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.openokr.common.constant.DailyConstant;
import org.openokr.db.service.IBasicDBService;
import org.openokr.db.service.IDailyDBService;
import org.openokr.task.entity.DailyEntity;
import org.openokr.task.request.DailySearchVO;
import org.openokr.task.vo.DailyVO;
import org.openokr.util.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yuxinzh
 * @create 2019/3/2
 */
@Service
@Transactional
public class DailyManageService extends BaseServiceImpl implements IDailyManageService {

    @Autowired
    private IDailyDBService dailyDBService;
    @Autowired
    private IBasicDBService basicDBService;

    @Override
    public Page queryPage(DailySearchVO condition, Page page) throws BusinessException {
        String methodName = "getDailyList-根据条件查询日报列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            //如果是管理员，并且要查所有人的日报
            List<DailyVO> list = new ArrayList<>();
            if (StringUtils.isNotBlank(condition.getIsAdmin())){
                List<String> userIdList = basicDBService.getUserIdListByAdminTeam(condition.getReportUserId());
                condition.setReportUserId(null);
                if (userIdList.size()>0){
                    condition.setReportUserIdList(userIdList);
                } else {
                    page.setTotalRecord(0);
                    page.setRecords(list);
                    return page;
                }
            }
            int count = dailyDBService.countDailyList(condition);
            if (count >= 0) {
                list = dailyDBService.getDailyList(condition,page);
            }
            if (page == null) {
                page = new Page();
            }
            page.setTotalRecord(count);
            page.setRecords(list);

            return page;
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw new BusinessException(e);
        }
    }

    @Override
    public List<DailyVO> getDailyList(DailySearchVO condition) throws BusinessException {
        String methodName = "getDailyList-根据条件查询日报列表";
        try {
            if (condition == null) {
                throw new BusinessException("查询条件对象为空");
            }
            return dailyDBService.getDailyList(condition,null);
        } catch (BusinessException e) {
            logger.error("{} 失败，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[condition]->{}",methodName, JSON.toJSONString(condition));
            throw new BusinessException(e);
        }
    }

    @Override
    public void insertDailyList(List<DailyVO> dailyList,String userId,String dateStr) throws BusinessException {
        String methodName = "insertDailyList-批量保存日报记录";
        try {
            if (dailyList == null || dailyList.isEmpty()){
                throw new BusinessException("没有需要保存的记录");
            }
            if (StringUtils.isBlank(userId)) {
                throw new BusinessException("用户ID为空");
            }
            if (StringUtils.isBlank(dateStr)) {
                throw new BusinessException("日报时间为空");
            }

            // 先查询是否已经保存过当日日报
//            DailySearchVO searchVO = new DailySearchVO();
//            searchVO.setReportDayStr(dateStr);
//            searchVO.setReportUserId(userId);
//            List<DailyVO> dailyVOList = this.getDailyList(searchVO);
//            if (dailyVOList != null && !dailyVOList.isEmpty()) {
//                throw new BusinessException("今日已填写过日报");
//            }
            logger.info("新增/更新日报 dailyList:{}",JSON.toJSONString(dailyList));
            for (DailyVO dailyVO:dailyList) {
                if (StringUtils.isNotBlank(dailyVO.getId())){
                    DailyEntity dailyEntity = this.selectByPrimaryKey(DailyEntity.class,dailyVO.getId());
                    if (dailyEntity == null){
                        throw new BusinessException("无法找到当前日报主键信息");
                    }
                    if (DailyConstant.DAILY_AUDIT_STATUS_PASS.equals(dailyEntity.getAuditStatus())){
                        throw new BusinessException("该日报已审批通过不可编辑");
                    }else {
                        dailyEntity.setAuditStatus(DailyConstant.DAILY_AUDIT_STATUS_WAITING);
                        dailyEntity.setRemark(dailyVO.getRemark());
                        dailyEntity.setDuration(dailyVO.getDuration());
                        dailyEntity.setReportDay(dailyVO.getReportDay());
                        dailyEntity.setTaskId(dailyVO.getTaskId());
                        dailyEntity.setUpdateUserId(userId);
                        dailyEntity.setUpdateTs(new Date());
                        this.update(dailyEntity);
                    }
                }else {
                    dailyVO.setId(null);
                    dailyVO.setReportDay(DateUtils.stringToDate(dateStr));
                    dailyVO.setReportUserId(userId);
                    dailyVO.setCreateUserId(userId);
                    dailyVO.setCreateTs(new Date());
                    dailyVO.setUpdateUserId(userId);
                    dailyVO.setUpdateTs(new Date());
                    dailyVO.setAuditStatus(DailyConstant.DAILY_AUDIT_STATUS_WAITING);
                    this.insertDailyData(dailyVO);
                }
            }
        } catch (BusinessException e) {
            logger.error("{} 失败，[dailyList]->{},[userId]->{},[dateStr]->{}",methodName, JSON.toJSONString(dailyList),userId,dateStr);
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[dailyList]->{},[userId]->{},[dateStr]->{}",methodName, JSON.toJSONString(dailyList),userId,dateStr);
            throw new BusinessException(e);
        }
    }

    @Override
    public void deleteDailyList(DailyVO dailyVO) throws BusinessException {
        String methodName = "deleteDailyList-删除日报记录";
        try {
            if (dailyVO == null){
                throw new BusinessException("保存对象为空");
            }
            if (StringUtils.isBlank(dailyVO.getId())) {
                throw new BusinessException("删除参数为空");
            }
            DailyEntity dailyEntity = this.selectByPrimaryKey(DailyEntity.class,dailyVO.getId());
            if (dailyEntity!=null){
                if (DailyConstant.DAILY_AUDIT_STATUS_PASS.equals(dailyEntity.getAuditStatus())){
                    throw new BusinessException("当前日报已审批，无法删除");
                }else {
                    this.deleteByPrimaryKey(DailyEntity.class,dailyVO.getId());
                }
            }else {
                logger.info("根据日报Id未查询到将要删除的日报信息 dailyVO:{}",JSON.toJSONString(dailyVO));
            }

        } catch (BusinessException e) {
            logger.error("{} 失败，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw new BusinessException(e);
        }

    }

    @Override
    public void auditDailyInfo(DailyVO dailyVO) throws BusinessException {
        String methodName = "auditDailyInfo-审核日报";
        try {
            if (dailyVO == null){
                throw new BusinessException("审核对象为空");
            }
            if (StringUtils.isBlank(dailyVO.getId())) {
                throw new BusinessException("参数日报ID为空");
            }
            Date date = new Date();
            DailyEntity dailyEntity = this.selectByPrimaryKey(DailyEntity.class,dailyVO.getId());
            if (dailyEntity!=null){
                dailyEntity.setAuditStatus(dailyVO.getAuditStatus());
                dailyEntity.setAuditUserId(dailyVO.getAuditUserId());
                dailyEntity.setAuditTime(date);
                dailyEntity.setUpdateUserId(dailyVO.getAuditUserId());
                dailyEntity.setUpdateTs(date);
                this.update(dailyEntity);
            }else {
                logger.info("根据日报Id未查询到将要审核的日报信息 dailyVO:{}",JSON.toJSONString(dailyVO));
            }

        } catch (BusinessException e) {
            logger.error("{} 失败，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw new BusinessException(e);
        }
    }

    private void insertDailyData(DailyVO dailyVO) throws BusinessException{
        String methodName = "insertDailyData-保存日报记录";
        try {
            if (dailyVO == null){
                throw new BusinessException("保存对象为空");
            }
            if (StringUtils.isBlank(dailyVO.getTaskId())) {
                throw new BusinessException("项目ID为空");
            }
            if (dailyVO.getDuration() == null) {
                throw new BusinessException("报工时长为空");
            }

            DailyEntity entity = new DailyEntity();
            BeanUtils.copyProperties(dailyVO,entity);
            this.insert(entity);
        } catch (BusinessException e) {
            logger.error("{} 失败，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw e;
        } catch (Exception e) {
            logger.error("{} 异常，[dailyVO]->{}",methodName, JSON.toJSONString(dailyVO));
            throw new BusinessException(e);
        }
    }
}
