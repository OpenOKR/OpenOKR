package org.openokr.manage.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import com.zzheng.framework.mybatis.entity.condition.BaseEntityCondition;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultsEntityCondition extends BaseEntityCondition implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ResultsEntityCondition() {
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
        return ResultsEntity.class;
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

        public Criteria andObjectIdIsNull() {
            addCriterion("object_id is null");
            return (Criteria) this;
        }

        public Criteria andObjectIdIsNotNull() {
            addCriterion("object_id is not null");
            return (Criteria) this;
        }

        public Criteria andObjectIdEqualTo(String value) {
            addCriterion("object_id =", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotEqualTo(String value) {
            addCriterion("object_id <>", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdGreaterThan(String value) {
            addCriterion("object_id >", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("object_id >=", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdLessThan(String value) {
            addCriterion("object_id <", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdLessThanOrEqualTo(String value) {
            addCriterion("object_id <=", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdLike(String value) {
            addCriterion("object_id like", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotLike(String value) {
            addCriterion("object_id not like", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdIn(List<String> values) {
            addCriterion("object_id in", values, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotIn(List<String> values) {
            addCriterion("object_id not in", values, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdBetween(String value1, String value2) {
            addCriterion("object_id between", value1, value2, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotBetween(String value1, String value2) {
            addCriterion("object_id not between", value1, value2, "objectId");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andMetricUnitIsNull() {
            addCriterion("metric_unit is null");
            return (Criteria) this;
        }

        public Criteria andMetricUnitIsNotNull() {
            addCriterion("metric_unit is not null");
            return (Criteria) this;
        }

        public Criteria andMetricUnitEqualTo(String value) {
            addCriterion("metric_unit =", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitNotEqualTo(String value) {
            addCriterion("metric_unit <>", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitGreaterThan(String value) {
            addCriterion("metric_unit >", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitGreaterThanOrEqualTo(String value) {
            addCriterion("metric_unit >=", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitLessThan(String value) {
            addCriterion("metric_unit <", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitLessThanOrEqualTo(String value) {
            addCriterion("metric_unit <=", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitLike(String value) {
            addCriterion("metric_unit like", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitNotLike(String value) {
            addCriterion("metric_unit not like", value, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitIn(List<String> values) {
            addCriterion("metric_unit in", values, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitNotIn(List<String> values) {
            addCriterion("metric_unit not in", values, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitBetween(String value1, String value2) {
            addCriterion("metric_unit between", value1, value2, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andMetricUnitNotBetween(String value1, String value2) {
            addCriterion("metric_unit not between", value1, value2, "metricUnit");
            return (Criteria) this;
        }

        public Criteria andTargetValueIsNull() {
            addCriterion("target_value is null");
            return (Criteria) this;
        }

        public Criteria andTargetValueIsNotNull() {
            addCriterion("target_value is not null");
            return (Criteria) this;
        }

        public Criteria andTargetValueEqualTo(String value) {
            addCriterion("target_value =", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueNotEqualTo(String value) {
            addCriterion("target_value <>", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueGreaterThan(String value) {
            addCriterion("target_value >", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueGreaterThanOrEqualTo(String value) {
            addCriterion("target_value >=", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueLessThan(String value) {
            addCriterion("target_value <", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueLessThanOrEqualTo(String value) {
            addCriterion("target_value <=", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueLike(String value) {
            addCriterion("target_value like", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueNotLike(String value) {
            addCriterion("target_value not like", value, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueIn(List<String> values) {
            addCriterion("target_value in", values, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueNotIn(List<String> values) {
            addCriterion("target_value not in", values, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueBetween(String value1, String value2) {
            addCriterion("target_value between", value1, value2, "targetValue");
            return (Criteria) this;
        }

        public Criteria andTargetValueNotBetween(String value1, String value2) {
            addCriterion("target_value not between", value1, value2, "targetValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueIsNull() {
            addCriterion("initial_value is null");
            return (Criteria) this;
        }

        public Criteria andInitialValueIsNotNull() {
            addCriterion("initial_value is not null");
            return (Criteria) this;
        }

        public Criteria andInitialValueEqualTo(String value) {
            addCriterion("initial_value =", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueNotEqualTo(String value) {
            addCriterion("initial_value <>", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueGreaterThan(String value) {
            addCriterion("initial_value >", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueGreaterThanOrEqualTo(String value) {
            addCriterion("initial_value >=", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueLessThan(String value) {
            addCriterion("initial_value <", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueLessThanOrEqualTo(String value) {
            addCriterion("initial_value <=", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueLike(String value) {
            addCriterion("initial_value like", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueNotLike(String value) {
            addCriterion("initial_value not like", value, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueIn(List<String> values) {
            addCriterion("initial_value in", values, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueNotIn(List<String> values) {
            addCriterion("initial_value not in", values, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueBetween(String value1, String value2) {
            addCriterion("initial_value between", value1, value2, "initialValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueNotBetween(String value1, String value2) {
            addCriterion("initial_value not between", value1, value2, "initialValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIsNull() {
            addCriterion("current_value is null");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIsNotNull() {
            addCriterion("current_value is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentValueEqualTo(String value) {
            addCriterion("current_value =", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotEqualTo(String value) {
            addCriterion("current_value <>", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueGreaterThan(String value) {
            addCriterion("current_value >", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueGreaterThanOrEqualTo(String value) {
            addCriterion("current_value >=", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLessThan(String value) {
            addCriterion("current_value <", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLessThanOrEqualTo(String value) {
            addCriterion("current_value <=", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLike(String value) {
            addCriterion("current_value like", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotLike(String value) {
            addCriterion("current_value not like", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIn(List<String> values) {
            addCriterion("current_value in", values, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotIn(List<String> values) {
            addCriterion("current_value not in", values, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueBetween(String value1, String value2) {
            addCriterion("current_value between", value1, value2, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotBetween(String value1, String value2) {
            addCriterion("current_value not between", value1, value2, "currentValue");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNull() {
            addCriterion("owner_id is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIsNotNull() {
            addCriterion("owner_id is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerIdEqualTo(String value) {
            addCriterion("owner_id =", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotEqualTo(String value) {
            addCriterion("owner_id <>", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThan(String value) {
            addCriterion("owner_id >", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdGreaterThanOrEqualTo(String value) {
            addCriterion("owner_id >=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThan(String value) {
            addCriterion("owner_id <", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLessThanOrEqualTo(String value) {
            addCriterion("owner_id <=", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLike(String value) {
            addCriterion("owner_id like", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotLike(String value) {
            addCriterion("owner_id not like", value, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdIn(List<String> values) {
            addCriterion("owner_id in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotIn(List<String> values) {
            addCriterion("owner_id not in", values, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdBetween(String value1, String value2) {
            addCriterion("owner_id between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andOwnerIdNotBetween(String value1, String value2) {
            addCriterion("owner_id not between", value1, value2, "ownerId");
            return (Criteria) this;
        }

        public Criteria andEndTsIsNull() {
            addCriterion("end_ts is null");
            return (Criteria) this;
        }

        public Criteria andEndTsIsNotNull() {
            addCriterion("end_ts is not null");
            return (Criteria) this;
        }

        public Criteria andEndTsEqualTo(Date value) {
            addCriterion("end_ts =", value, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsNotEqualTo(Date value) {
            addCriterion("end_ts <>", value, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsGreaterThan(Date value) {
            addCriterion("end_ts >", value, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsGreaterThanOrEqualTo(Date value) {
            addCriterion("end_ts >=", value, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsLessThan(Date value) {
            addCriterion("end_ts <", value, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsLessThanOrEqualTo(Date value) {
            addCriterion("end_ts <=", value, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsIn(List<Date> values) {
            addCriterion("end_ts in", values, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsNotIn(List<Date> values) {
            addCriterion("end_ts not in", values, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsBetween(Date value1, Date value2) {
            addCriterion("end_ts between", value1, value2, "endTs");
            return (Criteria) this;
        }

        public Criteria andEndTsNotBetween(Date value1, Date value2) {
            addCriterion("end_ts not between", value1, value2, "endTs");
            return (Criteria) this;
        }

        public Criteria andPreviousNameIsNull() {
            addCriterion("previous_name is null");
            return (Criteria) this;
        }

        public Criteria andPreviousNameIsNotNull() {
            addCriterion("previous_name is not null");
            return (Criteria) this;
        }

        public Criteria andPreviousNameEqualTo(String value) {
            addCriterion("previous_name =", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameNotEqualTo(String value) {
            addCriterion("previous_name <>", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameGreaterThan(String value) {
            addCriterion("previous_name >", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameGreaterThanOrEqualTo(String value) {
            addCriterion("previous_name >=", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameLessThan(String value) {
            addCriterion("previous_name <", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameLessThanOrEqualTo(String value) {
            addCriterion("previous_name <=", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameLike(String value) {
            addCriterion("previous_name like", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameNotLike(String value) {
            addCriterion("previous_name not like", value, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameIn(List<String> values) {
            addCriterion("previous_name in", values, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameNotIn(List<String> values) {
            addCriterion("previous_name not in", values, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameBetween(String value1, String value2) {
            addCriterion("previous_name between", value1, value2, "previousName");
            return (Criteria) this;
        }

        public Criteria andPreviousNameNotBetween(String value1, String value2) {
            addCriterion("previous_name not between", value1, value2, "previousName");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(String value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(String value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(String value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(String value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(String value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(String value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLike(String value) {
            addCriterion("del_flag like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotLike(String value) {
            addCriterion("del_flag not like", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<String> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<String> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(String value1, String value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(String value1, String value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andProgressIsNull() {
            addCriterion("progress is null");
            return (Criteria) this;
        }

        public Criteria andProgressIsNotNull() {
            addCriterion("progress is not null");
            return (Criteria) this;
        }

        public Criteria andProgressEqualTo(BigDecimal value) {
            addCriterion("progress =", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotEqualTo(BigDecimal value) {
            addCriterion("progress <>", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThan(BigDecimal value) {
            addCriterion("progress >", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("progress >=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThan(BigDecimal value) {
            addCriterion("progress <", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressLessThanOrEqualTo(BigDecimal value) {
            addCriterion("progress <=", value, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressIn(List<BigDecimal> values) {
            addCriterion("progress in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotIn(List<BigDecimal> values) {
            addCriterion("progress not in", values, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("progress between", value1, value2, "progress");
            return (Criteria) this;
        }

        public Criteria andProgressNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("progress not between", value1, value2, "progress");
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

        public Criteria andPreProgressIsNull() {
            addCriterion("pre_progress is null");
            return (Criteria) this;
        }

        public Criteria andPreProgressIsNotNull() {
            addCriterion("pre_progress is not null");
            return (Criteria) this;
        }

        public Criteria andPreProgressEqualTo(BigDecimal value) {
            addCriterion("pre_progress =", value, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressNotEqualTo(BigDecimal value) {
            addCriterion("pre_progress <>", value, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressGreaterThan(BigDecimal value) {
            addCriterion("pre_progress >", value, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_progress >=", value, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressLessThan(BigDecimal value) {
            addCriterion("pre_progress <", value, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_progress <=", value, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressIn(List<BigDecimal> values) {
            addCriterion("pre_progress in", values, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressNotIn(List<BigDecimal> values) {
            addCriterion("pre_progress not in", values, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_progress between", value1, value2, "preProgress");
            return (Criteria) this;
        }

        public Criteria andPreProgressNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_progress not between", value1, value2, "preProgress");
            return (Criteria) this;
        }

        public Criteria andIdLikeInsensitive(String value) {
            addCriterion("upper(id) like", value.toUpperCase(), "id");
            return (Criteria) this;
        }

        public Criteria andObjectIdLikeInsensitive(String value) {
            addCriterion("upper(object_id) like", value.toUpperCase(), "objectId");
            return (Criteria) this;
        }

        public Criteria andNameLikeInsensitive(String value) {
            addCriterion("upper(name) like", value.toUpperCase(), "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionLikeInsensitive(String value) {
            addCriterion("upper(description) like", value.toUpperCase(), "description");
            return (Criteria) this;
        }

        public Criteria andMetricUnitLikeInsensitive(String value) {
            addCriterion("upper(metric_unit) like", value.toUpperCase(), "metricUnit");
            return (Criteria) this;
        }

        public Criteria andTargetValueLikeInsensitive(String value) {
            addCriterion("upper(target_value) like", value.toUpperCase(), "targetValue");
            return (Criteria) this;
        }

        public Criteria andInitialValueLikeInsensitive(String value) {
            addCriterion("upper(initial_value) like", value.toUpperCase(), "initialValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLikeInsensitive(String value) {
            addCriterion("upper(current_value) like", value.toUpperCase(), "currentValue");
            return (Criteria) this;
        }

        public Criteria andOwnerIdLikeInsensitive(String value) {
            addCriterion("upper(owner_id) like", value.toUpperCase(), "ownerId");
            return (Criteria) this;
        }

        public Criteria andPreviousNameLikeInsensitive(String value) {
            addCriterion("upper(previous_name) like", value.toUpperCase(), "previousName");
            return (Criteria) this;
        }

        public Criteria andStatusLikeInsensitive(String value) {
            addCriterion("upper(status) like", value.toUpperCase(), "status");
            return (Criteria) this;
        }

        public Criteria andDelFlagLikeInsensitive(String value) {
            addCriterion("upper(del_flag) like", value.toUpperCase(), "delFlag");
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
        public Criteria andObjectIdLikeIgnoreCase(String value) {
            addCriterion("upper(object_id) like ", value.toUpperCase(), "objectId");
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
        public Criteria andNameLikeIgnoreCase(String value) {
            addCriterion("upper(name) like ", value.toUpperCase(), "name");
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
        public Criteria andDescriptionLikeIgnoreCase(String value) {
            addCriterion("upper(description) like ", value.toUpperCase(), "description");
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
        public Criteria andMetricUnitLikeIgnoreCase(String value) {
            addCriterion("upper(metric_unit) like ", value.toUpperCase(), "metricUnit");
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
        public Criteria andTargetValueLikeIgnoreCase(String value) {
            addCriterion("upper(target_value) like ", value.toUpperCase(), "targetValue");
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
        public Criteria andInitialValueLikeIgnoreCase(String value) {
            addCriterion("upper(initial_value) like ", value.toUpperCase(), "initialValue");
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
        public Criteria andCurrentValueLikeIgnoreCase(String value) {
            addCriterion("upper(current_value) like ", value.toUpperCase(), "currentValue");
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
        public Criteria andOwnerIdLikeIgnoreCase(String value) {
            addCriterion("upper(owner_id) like ", value.toUpperCase(), "ownerId");
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
        public Criteria andPreviousNameLikeIgnoreCase(String value) {
            addCriterion("upper(previous_name) like ", value.toUpperCase(), "previousName");
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
        public Criteria andStatusLikeIgnoreCase(String value) {
            addCriterion("upper(status) like ", value.toUpperCase(), "status");
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
        public Criteria andDelFlagLikeIgnoreCase(String value) {
            addCriterion("upper(del_flag) like ", value.toUpperCase(), "delFlag");
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