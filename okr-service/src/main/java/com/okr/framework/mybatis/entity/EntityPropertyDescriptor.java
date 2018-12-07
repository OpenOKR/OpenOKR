package com.okr.framework.mybatis.entity;

import java.lang.reflect.Method;

public class EntityPropertyDescriptor {
    private String columnName;
    private String fieldName;
    private String javaType;
    private String jdbcType;
    private boolean isPrimaryKey = false;
    private boolean isVersion = false;
    private boolean isTransient = false;
    private Method setMethod = null;
    private Method getMethod = null;
    private Method otherMethod = null;

    public EntityPropertyDescriptor() {
    }

    public EntityPropertyDescriptor(String fieldName) {
        this.fieldName = fieldName;
    }

    public EntityPropertyDescriptor(String fieldName, Method setMethod, Method getMethod) {
        this.fieldName = fieldName;
        this.setMethod = setMethod;
        this.getMethod = getMethod;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getJdbcType() {
        return this.jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJavaType() {
        return this.javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean isPrimaryKey() {
        return this.isPrimaryKey;
    }

    public void setPrimaryKey(boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public boolean isVersion() {
        return this.isVersion;
    }

    public void setVersion(boolean isVersion) {
        this.isVersion = isVersion;
    }

    public boolean isTransient() {
        return this.isTransient;
    }

    public void setTransient(boolean isTransient) {
        this.isTransient = isTransient;
    }

    public Method getSetMethod() {
        return this.setMethod;
    }

    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    public Method getGetMethod() {
        return this.getMethod;
    }

    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }

    public Method getOtherMethod() {
        return this.otherMethod;
    }

    public void setOtherMethod(Method otherMethod) {
        this.otherMethod = otherMethod;
    }
}

