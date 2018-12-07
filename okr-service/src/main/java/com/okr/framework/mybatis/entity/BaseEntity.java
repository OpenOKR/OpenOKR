package com.okr.framework.mybatis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.okr.framework.mybatis.entity.utils.EntityUtil;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -6437253381906993752L;
    public static final String DEFAULT_FIELD_NAME_PRIMARY_KEY = "id";
    protected String id;
    private List<String> settedFields = new ArrayList();
    private Integer rowState;

    public BaseEntity() {
        this.rowState = Integer.valueOf(BaseEntity.RowStateEnum.UNCHANGED.getValue());
    }

    public void setupSettedFieldArr(String[] fieldNameArr) {
        this.settedFields.clear();
        this.settedFields.addAll(Arrays.asList(fieldNameArr));
    }

    public void setupSettedFields(List<String> settedFields) {
        this.settedFields = settedFields;
    }

    protected void addSettedField(String fieldName) {
        if(!this.settedFields.contains(fieldName)) {
            this.settedFields.add(fieldName);
        }

    }

    public List<String> obtainSettedFields() {
        return this.settedFields;
    }

    @Transient
    public Integer getRowState() {
        return this.rowState;
    }

    public void setRowState(Integer rowState) {
        this.rowState = rowState;
    }

    public void setId(String id) {
        this.id = id;
        this.addSettedField("id");
    }

    @Column(
            name = "id"
    )
    @Id
    @GeneratedValue(
            generator = "UUIDGenerator"
    )
    public String getId() {
        return this.id;
    }

    public Class<?> obtainEntityMapperClass() {
        throw new RuntimeException(this.getClass().getName() + " not implement the obtainEntityMapperClass method!");
    }

    public void setAttributeValue(String field, Object value) {
        if(field != null && field.length() < 1) {
            throw new RuntimeException("field name is must.");
        } else {
            EntityUtil.setFieldValueByFieldName(this, field, value);
        }
    }

    @Transient
    public Object getAttributeValue(String field) {
        if(field != null && field.length() < 1) {
            return null;
        } else {
            Serializable value = null;

            try {
                value = EntityUtil.getFieldValueByFieldName(this, field);
                return value;
            } catch (Exception var4) {
                throw new RuntimeException(var4.getMessage(), var4);
            }
        }
    }

    public static enum RowStateEnum {
        UNCHANGED(0),
        ADDED(1),
        DELETED(2),
        MODIFIED(3);

        private int value;

        private RowStateEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
