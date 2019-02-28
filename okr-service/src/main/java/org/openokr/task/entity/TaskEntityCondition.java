package org.openokr.task.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import com.zzheng.framework.mybatis.entity.condition.BaseEntityCondition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskEntityCondition extends BaseEntityCondition implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaskEntityCondition() {
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
        return TaskEntity.class;
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

        public Criteria andTaskCodeIsNull() {
            addCriterion("task_code is null");
            return (Criteria) this;
        }

        public Criteria andTaskCodeIsNotNull() {
            addCriterion("task_code is not null");
            return (Criteria) this;
        }

        public Criteria andTaskCodeEqualTo(String value) {
            addCriterion("task_code =", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeNotEqualTo(String value) {
            addCriterion("task_code <>", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeGreaterThan(String value) {
            addCriterion("task_code >", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeGreaterThanOrEqualTo(String value) {
            addCriterion("task_code >=", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeLessThan(String value) {
            addCriterion("task_code <", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeLessThanOrEqualTo(String value) {
            addCriterion("task_code <=", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeLike(String value) {
            addCriterion("task_code like", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeNotLike(String value) {
            addCriterion("task_code not like", value, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeIn(List<String> values) {
            addCriterion("task_code in", values, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeNotIn(List<String> values) {
            addCriterion("task_code not in", values, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeBetween(String value1, String value2) {
            addCriterion("task_code between", value1, value2, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskCodeNotBetween(String value1, String value2) {
            addCriterion("task_code not between", value1, value2, "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNull() {
            addCriterion("task_name is null");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNotNull() {
            addCriterion("task_name is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNameEqualTo(String value) {
            addCriterion("task_name =", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotEqualTo(String value) {
            addCriterion("task_name <>", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThan(String value) {
            addCriterion("task_name >", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
            addCriterion("task_name >=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThan(String value) {
            addCriterion("task_name <", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThanOrEqualTo(String value) {
            addCriterion("task_name <=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLike(String value) {
            addCriterion("task_name like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotLike(String value) {
            addCriterion("task_name not like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameIn(List<String> values) {
            addCriterion("task_name in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotIn(List<String> values) {
            addCriterion("task_name not in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameBetween(String value1, String value2) {
            addCriterion("task_name between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotBetween(String value1, String value2) {
            addCriterion("task_name not between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNull() {
            addCriterion("task_status is null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNotNull() {
            addCriterion("task_status is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusEqualTo(String value) {
            addCriterion("task_status =", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotEqualTo(String value) {
            addCriterion("task_status <>", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThan(String value) {
            addCriterion("task_status >", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThanOrEqualTo(String value) {
            addCriterion("task_status >=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThan(String value) {
            addCriterion("task_status <", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThanOrEqualTo(String value) {
            addCriterion("task_status <=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLike(String value) {
            addCriterion("task_status like", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotLike(String value) {
            addCriterion("task_status not like", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIn(List<String> values) {
            addCriterion("task_status in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotIn(List<String> values) {
            addCriterion("task_status not in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusBetween(String value1, String value2) {
            addCriterion("task_status between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotBetween(String value1, String value2) {
            addCriterion("task_status not between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(String value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(String value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(String value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(String value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(String value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(String value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLike(String value) {
            addCriterion("is_deleted like", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotLike(String value) {
            addCriterion("is_deleted not like", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<String> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<String> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(String value1, String value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(String value1, String value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeIsNull() {
            addCriterion("task_start_time is null");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeIsNotNull() {
            addCriterion("task_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeEqualTo(Date value) {
            addCriterion("task_start_time =", value, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeNotEqualTo(Date value) {
            addCriterion("task_start_time <>", value, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeGreaterThan(Date value) {
            addCriterion("task_start_time >", value, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("task_start_time >=", value, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeLessThan(Date value) {
            addCriterion("task_start_time <", value, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("task_start_time <=", value, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeIn(List<Date> values) {
            addCriterion("task_start_time in", values, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeNotIn(List<Date> values) {
            addCriterion("task_start_time not in", values, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeBetween(Date value1, Date value2) {
            addCriterion("task_start_time between", value1, value2, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("task_start_time not between", value1, value2, "taskStartTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeIsNull() {
            addCriterion("task_end_time is null");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeIsNotNull() {
            addCriterion("task_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeEqualTo(Date value) {
            addCriterion("task_end_time =", value, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeNotEqualTo(Date value) {
            addCriterion("task_end_time <>", value, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeGreaterThan(Date value) {
            addCriterion("task_end_time >", value, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("task_end_time >=", value, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeLessThan(Date value) {
            addCriterion("task_end_time <", value, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("task_end_time <=", value, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeIn(List<Date> values) {
            addCriterion("task_end_time in", values, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeNotIn(List<Date> values) {
            addCriterion("task_end_time not in", values, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeBetween(Date value1, Date value2) {
            addCriterion("task_end_time between", value1, value2, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andTaskEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("task_end_time not between", value1, value2, "taskEndTime");
            return (Criteria) this;
        }

        public Criteria andJiraLabelIsNull() {
            addCriterion("jira_label is null");
            return (Criteria) this;
        }

        public Criteria andJiraLabelIsNotNull() {
            addCriterion("jira_label is not null");
            return (Criteria) this;
        }

        public Criteria andJiraLabelEqualTo(String value) {
            addCriterion("jira_label =", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelNotEqualTo(String value) {
            addCriterion("jira_label <>", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelGreaterThan(String value) {
            addCriterion("jira_label >", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelGreaterThanOrEqualTo(String value) {
            addCriterion("jira_label >=", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelLessThan(String value) {
            addCriterion("jira_label <", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelLessThanOrEqualTo(String value) {
            addCriterion("jira_label <=", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelLike(String value) {
            addCriterion("jira_label like", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelNotLike(String value) {
            addCriterion("jira_label not like", value, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelIn(List<String> values) {
            addCriterion("jira_label in", values, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelNotIn(List<String> values) {
            addCriterion("jira_label not in", values, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelBetween(String value1, String value2) {
            addCriterion("jira_label between", value1, value2, "jiraLabel");
            return (Criteria) this;
        }

        public Criteria andJiraLabelNotBetween(String value1, String value2) {
            addCriterion("jira_label not between", value1, value2, "jiraLabel");
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

        public Criteria andIdLikeInsensitive(String value) {
            addCriterion("upper(id) like", value.toUpperCase(), "id");
            return (Criteria) this;
        }

        public Criteria andTaskCodeLikeInsensitive(String value) {
            addCriterion("upper(task_code) like", value.toUpperCase(), "taskCode");
            return (Criteria) this;
        }

        public Criteria andTaskNameLikeInsensitive(String value) {
            addCriterion("upper(task_name) like", value.toUpperCase(), "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLikeInsensitive(String value) {
            addCriterion("upper(task_status) like", value.toUpperCase(), "taskStatus");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLikeInsensitive(String value) {
            addCriterion("upper(is_deleted) like", value.toUpperCase(), "isDeleted");
            return (Criteria) this;
        }

        public Criteria andJiraLabelLikeInsensitive(String value) {
            addCriterion("upper(jira_label) like", value.toUpperCase(), "jiraLabel");
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
        public Criteria andTaskCodeLikeIgnoreCase(String value) {
            addCriterion("upper(task_code) like ", value.toUpperCase(), "taskCode");
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
        public Criteria andTaskNameLikeIgnoreCase(String value) {
            addCriterion("upper(task_name) like ", value.toUpperCase(), "taskName");
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
        public Criteria andTaskStatusLikeIgnoreCase(String value) {
            addCriterion("upper(task_status) like ", value.toUpperCase(), "taskStatus");
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
        public Criteria andIsDeletedLikeIgnoreCase(String value) {
            addCriterion("upper(is_deleted) like ", value.toUpperCase(), "isDeleted");
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
        public Criteria andJiraLabelLikeIgnoreCase(String value) {
            addCriterion("upper(jira_label) like ", value.toUpperCase(), "jiraLabel");
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