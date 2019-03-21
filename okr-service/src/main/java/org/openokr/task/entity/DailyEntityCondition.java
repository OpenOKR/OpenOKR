package org.openokr.task.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import com.zzheng.framework.mybatis.entity.condition.BaseEntityCondition;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DailyEntityCondition extends BaseEntityCondition implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DailyEntityCondition() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    @Override
    public Class<? extends BaseEntity> getEntityClass() {
        return DailyEntity.class;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIsNull() {
            addCriterion("report_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIsNotNull() {
            addCriterion("report_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReportUserIdEqualTo(String value) {
            addCriterion("report_user_id =", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotEqualTo(String value) {
            addCriterion("report_user_id <>", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdGreaterThan(String value) {
            addCriterion("report_user_id >", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("report_user_id >=", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLessThan(String value) {
            addCriterion("report_user_id <", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLessThanOrEqualTo(String value) {
            addCriterion("report_user_id <=", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLike(String value) {
            addCriterion("report_user_id like", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotLike(String value) {
            addCriterion("report_user_id not like", value, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdIn(List<String> values) {
            addCriterion("report_user_id in", values, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotIn(List<String> values) {
            addCriterion("report_user_id not in", values, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdBetween(String value1, String value2) {
            addCriterion("report_user_id between", value1, value2, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andReportUserIdNotBetween(String value1, String value2) {
            addCriterion("report_user_id not between", value1, value2, "reportUserId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(String value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(String value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(String value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(String value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(String value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLike(String value) {
            addCriterion("task_id like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotLike(String value) {
            addCriterion("task_id not like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<String> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<String> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(String value1, String value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(String value1, String value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andReportDayIsNull() {
            addCriterion("report_day is null");
            return (Criteria) this;
        }

        public Criteria andReportDayIsNotNull() {
            addCriterion("report_day is not null");
            return (Criteria) this;
        }

        public Criteria andReportDayEqualTo(Date value) {
            addCriterionForJDBCDate("report_day =", value, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayNotEqualTo(Date value) {
            addCriterionForJDBCDate("report_day <>", value, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayGreaterThan(Date value) {
            addCriterionForJDBCDate("report_day >", value, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("report_day >=", value, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayLessThan(Date value) {
            addCriterionForJDBCDate("report_day <", value, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("report_day <=", value, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayIn(List<Date> values) {
            addCriterionForJDBCDate("report_day in", values, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayNotIn(List<Date> values) {
            addCriterionForJDBCDate("report_day not in", values, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("report_day between", value1, value2, "reportDay");
            return (Criteria) this;
        }

        public Criteria andReportDayNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("report_day not between", value1, value2, "reportDay");
            return (Criteria) this;
        }

        public Criteria andDurationIsNull() {
            addCriterion("duration is null");
            return (Criteria) this;
        }

        public Criteria andDurationIsNotNull() {
            addCriterion("duration is not null");
            return (Criteria) this;
        }

        public Criteria andDurationEqualTo(BigDecimal value) {
            addCriterion("duration =", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotEqualTo(BigDecimal value) {
            addCriterion("duration <>", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThan(BigDecimal value) {
            addCriterion("duration >", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("duration >=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThan(BigDecimal value) {
            addCriterion("duration <", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationLessThanOrEqualTo(BigDecimal value) {
            addCriterion("duration <=", value, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationIn(List<BigDecimal> values) {
            addCriterion("duration in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotIn(List<BigDecimal> values) {
            addCriterion("duration not in", values, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("duration between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andDurationNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("duration not between", value1, value2, "duration");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("create_user_id like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("create_user_id not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateTsIsNull() {
            addCriterion("create_ts is null");
            return (Criteria) this;
        }

        public Criteria andCreateTsIsNotNull() {
            addCriterion("create_ts is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTsEqualTo(Date value) {
            addCriterion("create_ts =", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotEqualTo(Date value) {
            addCriterion("create_ts <>", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsGreaterThan(Date value) {
            addCriterion("create_ts >", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsGreaterThanOrEqualTo(Date value) {
            addCriterion("create_ts >=", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsLessThan(Date value) {
            addCriterion("create_ts <", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsLessThanOrEqualTo(Date value) {
            addCriterion("create_ts <=", value, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsIn(List<Date> values) {
            addCriterion("create_ts in", values, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotIn(List<Date> values) {
            addCriterion("create_ts not in", values, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsBetween(Date value1, Date value2) {
            addCriterion("create_ts between", value1, value2, "createTs");
            return (Criteria) this;
        }

        public Criteria andCreateTsNotBetween(Date value1, Date value2) {
            addCriterion("create_ts not between", value1, value2, "createTs");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(String value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(String value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(String value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(String value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(String value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLike(String value) {
            addCriterion("update_user_id like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotLike(String value) {
            addCriterion("update_user_id not like", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<String> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<String> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(String value1, String value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(String value1, String value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateTsIsNull() {
            addCriterion("update_ts is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTsIsNotNull() {
            addCriterion("update_ts is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTsEqualTo(Date value) {
            addCriterion("update_ts =", value, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsNotEqualTo(Date value) {
            addCriterion("update_ts <>", value, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsGreaterThan(Date value) {
            addCriterion("update_ts >", value, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsGreaterThanOrEqualTo(Date value) {
            addCriterion("update_ts >=", value, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsLessThan(Date value) {
            addCriterion("update_ts <", value, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsLessThanOrEqualTo(Date value) {
            addCriterion("update_ts <=", value, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsIn(List<Date> values) {
            addCriterion("update_ts in", values, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsNotIn(List<Date> values) {
            addCriterion("update_ts not in", values, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsBetween(Date value1, Date value2) {
            addCriterion("update_ts between", value1, value2, "updateTs");
            return (Criteria) this;
        }

        public Criteria andUpdateTsNotBetween(Date value1, Date value2) {
            addCriterion("update_ts not between", value1, value2, "updateTs");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNull() {
            addCriterion("audit_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNotNull() {
            addCriterion("audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusEqualTo(String value) {
            addCriterion("audit_status =", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotEqualTo(String value) {
            addCriterion("audit_status <>", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThan(String value) {
            addCriterion("audit_status >", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThanOrEqualTo(String value) {
            addCriterion("audit_status >=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThan(String value) {
            addCriterion("audit_status <", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThanOrEqualTo(String value) {
            addCriterion("audit_status <=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLike(String value) {
            addCriterion("audit_status like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotLike(String value) {
            addCriterion("audit_status not like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIn(List<String> values) {
            addCriterion("audit_status in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotIn(List<String> values) {
            addCriterion("audit_status not in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusBetween(String value1, String value2) {
            addCriterion("audit_status between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotBetween(String value1, String value2) {
            addCriterion("audit_status not between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andIdLikeInsensitive(String value) {
            addCriterion("upper(id) like", value.toUpperCase(), "id");
            return (Criteria) this;
        }

        public Criteria andReportUserIdLikeInsensitive(String value) {
            addCriterion("upper(report_user_id) like", value.toUpperCase(), "reportUserId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLikeInsensitive(String value) {
            addCriterion("upper(task_id) like", value.toUpperCase(), "taskId");
            return (Criteria) this;
        }

        public Criteria andRemarkLikeInsensitive(String value) {
            addCriterion("upper(remark) like", value.toUpperCase(), "remark");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLikeInsensitive(String value) {
            addCriterion("upper(create_user_id) like", value.toUpperCase(), "createUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLikeInsensitive(String value) {
            addCriterion("upper(update_user_id) like", value.toUpperCase(), "updateUserId");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLikeInsensitive(String value) {
            addCriterion("upper(audit_status) like", value.toUpperCase(), "auditStatus");
            return (Criteria) this;
        }

        /**
         *(扩展Mybatis原生like的不足)
         *忽略字段大小写的模糊查询
         *Java编码如下：
         *  criteria.andNameLikeIgnoreCase("%Abc%"); 前后模糊,A字母大写
         *  criteria.andName2LikeIgnoreCase("%aBc"); 前模糊,B字母大写
         *  criteria.andName3LikeIgnoreCase("abC%"); 后模糊,C字母大写
         *执行时SQL如下:
         *  where name like '%abc%' or name2 like '%abc' or name3 like 'abc%'
         */
        public Criteria andIdLikeIgnoreCase(String value) {
            addCriterion("upper(id) like ", value.toUpperCase(), "id");
            return (Criteria) this;
        }

        /**
         *(扩展Mybatis原生like的不足)
         *忽略字段大小写的模糊查询
         *Java编码如下：
         *  criteria.andNameLikeIgnoreCase("%Abc%"); 前后模糊,A字母大写
         *  criteria.andName2LikeIgnoreCase("%aBc"); 前模糊,B字母大写
         *  criteria.andName3LikeIgnoreCase("abC%"); 后模糊,C字母大写
         *执行时SQL如下:
         *  where name like '%abc%' or name2 like '%abc' or name3 like 'abc%'
         */
        public Criteria andReportUserIdLikeIgnoreCase(String value) {
            addCriterion("upper(report_user_id) like ", value.toUpperCase(), "reportUserId");
            return (Criteria) this;
        }

        /**
         *(扩展Mybatis原生like的不足)
         *忽略字段大小写的模糊查询
         *Java编码如下：
         *  criteria.andNameLikeIgnoreCase("%Abc%"); 前后模糊,A字母大写
         *  criteria.andName2LikeIgnoreCase("%aBc"); 前模糊,B字母大写
         *  criteria.andName3LikeIgnoreCase("abC%"); 后模糊,C字母大写
         *执行时SQL如下:
         *  where name like '%abc%' or name2 like '%abc' or name3 like 'abc%'
         */
        public Criteria andTaskIdLikeIgnoreCase(String value) {
            addCriterion("upper(task_id) like ", value.toUpperCase(), "taskId");
            return (Criteria) this;
        }

        /**
         *(扩展Mybatis原生like的不足)
         *忽略字段大小写的模糊查询
         *Java编码如下：
         *  criteria.andNameLikeIgnoreCase("%Abc%"); 前后模糊,A字母大写
         *  criteria.andName2LikeIgnoreCase("%aBc"); 前模糊,B字母大写
         *  criteria.andName3LikeIgnoreCase("abC%"); 后模糊,C字母大写
         *执行时SQL如下:
         *  where name like '%abc%' or name2 like '%abc' or name3 like 'abc%'
         */
        public Criteria andRemarkLikeIgnoreCase(String value) {
            addCriterion("upper(remark) like ", value.toUpperCase(), "remark");
            return (Criteria) this;
        }

        /**
         *(扩展Mybatis原生like的不足)
         *忽略字段大小写的模糊查询
         *Java编码如下：
         *  criteria.andNameLikeIgnoreCase("%Abc%"); 前后模糊,A字母大写
         *  criteria.andName2LikeIgnoreCase("%aBc"); 前模糊,B字母大写
         *  criteria.andName3LikeIgnoreCase("abC%"); 后模糊,C字母大写
         *执行时SQL如下:
         *  where name like '%abc%' or name2 like '%abc' or name3 like 'abc%'
         */
        public Criteria andCreateUserIdLikeIgnoreCase(String value) {
            addCriterion("upper(create_user_id) like ", value.toUpperCase(), "createUserId");
            return (Criteria) this;
        }

        /**
         *(扩展Mybatis原生like的不足)
         *忽略字段大小写的模糊查询
         *Java编码如下：
         *  criteria.andNameLikeIgnoreCase("%Abc%"); 前后模糊,A字母大写
         *  criteria.andName2LikeIgnoreCase("%aBc"); 前模糊,B字母大写
         *  criteria.andName3LikeIgnoreCase("abC%"); 后模糊,C字母大写
         *执行时SQL如下:
         *  where name like '%abc%' or name2 like '%abc' or name3 like 'abc%'
         */
        public Criteria andUpdateUserIdLikeIgnoreCase(String value) {
            addCriterion("upper(update_user_id) like ", value.toUpperCase(), "updateUserId");
            return (Criteria) this;
        }

        /**
         *(扩展Mybatis原生like的不足)
         *忽略字段大小写的模糊查询
         *Java编码如下：
         *  criteria.andNameLikeIgnoreCase("%Abc%"); 前后模糊,A字母大写
         *  criteria.andName2LikeIgnoreCase("%aBc"); 前模糊,B字母大写
         *  criteria.andName3LikeIgnoreCase("abC%"); 后模糊,C字母大写
         *执行时SQL如下:
         *  where name like '%abc%' or name2 like '%abc' or name3 like 'abc%'
         */
        public Criteria andAuditStatusLikeIgnoreCase(String value) {
            addCriterion("upper(audit_status) like ", value.toUpperCase(), "auditStatus");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}