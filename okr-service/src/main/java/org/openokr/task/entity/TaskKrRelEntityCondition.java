package org.openokr.task.entity;

import com.zzheng.framework.mybatis.entity.BaseEntity;
import com.zzheng.framework.mybatis.entity.condition.BaseEntityCondition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskKrRelEntityCondition extends BaseEntityCondition implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaskKrRelEntityCondition() {
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
        return TaskKrRelEntity.class;
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

        public Criteria andKrIdIsNull() {
            addCriterion("kr_id is null");
            return (Criteria) this;
        }

        public Criteria andKrIdIsNotNull() {
            addCriterion("kr_id is not null");
            return (Criteria) this;
        }

        public Criteria andKrIdEqualTo(String value) {
            addCriterion("kr_id =", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdNotEqualTo(String value) {
            addCriterion("kr_id <>", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdGreaterThan(String value) {
            addCriterion("kr_id >", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdGreaterThanOrEqualTo(String value) {
            addCriterion("kr_id >=", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdLessThan(String value) {
            addCriterion("kr_id <", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdLessThanOrEqualTo(String value) {
            addCriterion("kr_id <=", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdLike(String value) {
            addCriterion("kr_id like", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdNotLike(String value) {
            addCriterion("kr_id not like", value, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdIn(List<String> values) {
            addCriterion("kr_id in", values, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdNotIn(List<String> values) {
            addCriterion("kr_id not in", values, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdBetween(String value1, String value2) {
            addCriterion("kr_id between", value1, value2, "krId");
            return (Criteria) this;
        }

        public Criteria andKrIdNotBetween(String value1, String value2) {
            addCriterion("kr_id not between", value1, value2, "krId");
            return (Criteria) this;
        }

        public Criteria andIdLikeInsensitive(String value) {
            addCriterion("upper(id) like", value.toUpperCase(), "id");
            return (Criteria) this;
        }

        public Criteria andTaskIdLikeInsensitive(String value) {
            addCriterion("upper(task_id) like", value.toUpperCase(), "taskId");
            return (Criteria) this;
        }

        public Criteria andKrIdLikeInsensitive(String value) {
            addCriterion("upper(kr_id) like", value.toUpperCase(), "krId");
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
        public Criteria andKrIdLikeIgnoreCase(String value) {
            addCriterion("upper(kr_id) like ", value.toUpperCase(), "krId");
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