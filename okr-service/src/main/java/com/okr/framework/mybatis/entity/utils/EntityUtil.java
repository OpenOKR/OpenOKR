package com.okr.framework.mybatis.entity.utils;


import com.okr.framework.mybatis.entity.BaseEntity;
import com.okr.framework.mybatis.entity.EntityPropertyDescriptor;
import com.okr.framework.spring.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EntityUtil {
    public EntityUtil() {
    }

    public static <Entity extends BaseEntity> String getTableName(Class<Entity> entityClass) {
        return entityClass != null && entityClass.isAnnotationPresent(Table.class)?((Table)entityClass.getAnnotation(Table.class)).name():null;
    }

    public static <Entity extends BaseEntity> String getPrimaryKeyFieldName(Class<Entity> entityClass) {
        List entityPropertyDescriptors = getEntityPropertyDescriptor(entityClass);
        String fieldName = null;
        Iterator i$ = entityPropertyDescriptors.iterator();

        while(i$.hasNext()) {
            EntityPropertyDescriptor entityPropertyDescriptor = (EntityPropertyDescriptor)i$.next();
            if(entityPropertyDescriptor.isPrimaryKey()) {
                fieldName = entityPropertyDescriptor.getFieldName();
                break;
            }
        }

        return fieldName;
    }

    public static <Entity extends BaseEntity> String getPrimaryKeyColumnName(Class<Entity> entityClass) {
        List entityPropertyDescriptors = getEntityPropertyDescriptor(entityClass);
        String columnName = null;
        Iterator i$ = entityPropertyDescriptors.iterator();

        while(i$.hasNext()) {
            EntityPropertyDescriptor entityPropertyDescriptor = (EntityPropertyDescriptor)i$.next();
            if(entityPropertyDescriptor.isPrimaryKey()) {
                columnName = entityPropertyDescriptor.getColumnName();
                break;
            }
        }

        return columnName;
    }

    public static <Entity extends BaseEntity> Serializable getPrimaryKeyValue(Entity entity) {
        return getFieldValueByFieldName(entity, getPrimaryKeyFieldName(entity.getClass()));
    }

    public static <Entity extends BaseEntity> String getVersionFileName(Class<Entity> entityClass) {
        List entityPropertyDescriptors = getEntityPropertyDescriptor(entityClass);
        String fileName = null;
        Iterator i$ = entityPropertyDescriptors.iterator();

        while(i$.hasNext()) {
            EntityPropertyDescriptor entityPropertyDescriptor = (EntityPropertyDescriptor)i$.next();
            if(entityPropertyDescriptor.isVersion()) {
                fileName = entityPropertyDescriptor.getFieldName();
                break;
            }
        }

        return fileName;
    }

    public static <Entity extends BaseEntity> String getVersionColumnName(Class<Entity> entityClass) {
        List entityPropertyDescriptors = getEntityPropertyDescriptor(entityClass);
        String columnName = null;
        Iterator i$ = entityPropertyDescriptors.iterator();

        while(i$.hasNext()) {
            EntityPropertyDescriptor entityPropertyDescriptor = (EntityPropertyDescriptor)i$.next();
            if(entityPropertyDescriptor.isVersion()) {
                columnName = entityPropertyDescriptor.getColumnName();
                break;
            }
        }

        return columnName;
    }

    public static <Entity extends BaseEntity> Serializable getVersionValue(Entity entity) {
        return getFieldValueByFieldName(entity, getVersionFileName(entity.getClass()));
    }

    public static <Entity extends BaseEntity> Method getGetMethodByFieldName(Class<Entity> entityClass, String fieldName) {
        return getPropertyDescriptorByFieldName(entityClass, fieldName).getGetMethod();
    }

    public static <Entity extends BaseEntity> Method getSetMethodByFieldName(Class<Entity> entityClass, String fieldName) {
        return getPropertyDescriptorByFieldName(entityClass, fieldName).getSetMethod();
    }

    public static <Entity extends BaseEntity> Serializable getFieldValueByFieldName(Entity entity, String fieldName) {
        return !StringUtils.isEmpty(fieldName)?(Serializable)(new BeanWrapperImpl(entity)).getPropertyValue(fieldName):null;
    }

    public static <Entity extends BaseEntity> void setFieldValueByFieldName(Entity entity, String fieldName, Object value) {
        if(!StringUtils.isEmpty(fieldName)) {
            (new BeanWrapperImpl(entity)).setPropertyValue(fieldName, value);
        }

    }

    public static <Entity extends BaseEntity> List<EntityPropertyDescriptor> getEntityPropertyDescriptor(Class<Entity> clazz) {
        return EntityUtil.EntityPropertyDescriptorManager.getEntityPropertyDescriptors(clazz);
    }

    public static <Entity extends BaseEntity> EntityPropertyDescriptor getPropertyDescriptorByFieldName(Class<Entity> clazz, String fieldName) {
        return EntityUtil.EntityPropertyDescriptorManager.getPropertyDescriptorByFieldName(clazz, fieldName);
    }

    public static <Entity extends BaseEntity> EntityPropertyDescriptor getPropertyDescriptorByColumnName(Class<Entity> clazz, String columnName) {
        return EntityUtil.EntityPropertyDescriptorManager.getPropertyDescriptorByColumnName(clazz, columnName);
    }

    public static <Entity extends BaseEntity> String getColumnNameByFieldName(Class<Entity> clazz, String fieldName) {
        EntityPropertyDescriptor entityPropertyDescriptor = EntityUtil.EntityPropertyDescriptorManager.getPropertyDescriptorByFieldName(clazz, fieldName);
        return entityPropertyDescriptor.getColumnName();
    }

    public static <Entity extends BaseEntity> String getFieldNameByColumnName(Class<Entity> clazz, String columnName) {
        EntityPropertyDescriptor entityPropertyDescriptor = EntityUtil.EntityPropertyDescriptorManager.getPropertyDescriptorByColumnName(clazz, columnName);
        return entityPropertyDescriptor.getFieldName();
    }

    public static <Entity extends BaseEntity> Method getMethodByMethodName(Class<Entity> clazz, String methodName) {
        return EntityUtil.EntityPropertyDescriptorManager.getMethodByMethodName(clazz, methodName);
    }

    public static <Entity extends BaseEntity> void setSettedFieldArr(List<Entity> entities, String[] fieldNameArr) {
        Iterator i$ = entities.iterator();

        while(i$.hasNext()) {
            BaseEntity entity = (BaseEntity)i$.next();
            entity.setupSettedFieldArr(fieldNameArr);
        }

    }

    public static <Entity extends BaseEntity> List<String> getAllFieldName(Class<Entity> clazz) {
        List entityPropertyDescriptors = EntityUtil.EntityPropertyDescriptorManager.getEntityPropertyDescriptors(clazz);
        ArrayList fieldNameList = new ArrayList();
        Iterator i$ = entityPropertyDescriptors.iterator();

        while(i$.hasNext()) {
            EntityPropertyDescriptor entityPropertyDescriptor = (EntityPropertyDescriptor)i$.next();
            if(entityPropertyDescriptor.getFieldName() != null) {
                fieldNameList.add(entityPropertyDescriptor.getFieldName());
            }
        }

        return fieldNameList;
    }

    public static <Entity extends BaseEntity> List<String> getAllColumnName(Class<Entity> clazz) {
        List entityPropertyDescriptors = EntityUtil.EntityPropertyDescriptorManager.getEntityPropertyDescriptors(clazz);
        ArrayList columnNameList = new ArrayList();
        Iterator i$ = entityPropertyDescriptors.iterator();

        while(i$.hasNext()) {
            EntityPropertyDescriptor entityPropertyDescriptor = (EntityPropertyDescriptor)i$.next();
            if(entityPropertyDescriptor.getColumnName() != null) {
                columnNameList.add(entityPropertyDescriptor.getColumnName());
            }
        }

        return columnNameList;
    }

    public static <Entity extends BaseEntity> Map<String, String> getColumnFieldMap(Class<Entity> clazz) {
        List entityPropertyDescriptors = EntityUtil.EntityPropertyDescriptorManager.getEntityPropertyDescriptors(clazz);
        HashMap columnFieldMap = new HashMap();
        Iterator i$ = entityPropertyDescriptors.iterator();

        while(i$.hasNext()) {
            EntityPropertyDescriptor entityPropertyDescriptor = (EntityPropertyDescriptor)i$.next();
            if(entityPropertyDescriptor.getColumnName() != null) {
                columnFieldMap.put(entityPropertyDescriptor.getColumnName(), entityPropertyDescriptor.getFieldName());
            }
        }

        return columnFieldMap;
    }

    static class EntityPropertyDescriptorManager {
        private static transient Map<Class<? extends BaseEntity>, List<EntityPropertyDescriptor>> propertyDescriptorMap = new HashMap();

        EntityPropertyDescriptorManager() {
        }

        public static synchronized <Entity extends BaseEntity> List<EntityPropertyDescriptor> getEntityPropertyDescriptors(Class<Entity> clazz) {
            if(propertyDescriptorMap.containsKey(clazz)) {
                return (List)propertyDescriptorMap.get(clazz);
            } else {
                ArrayList propertyDescriptors = new ArrayList();
                BeanWrapper beanWrapper = new BeanWrapper(clazz);
                PropertyDescriptor[] arr$ = beanWrapper.getPropertyDescriptorsInDeclaringOrder();
                int len$ = arr$.length;

                int i$;
                EntityPropertyDescriptor entityPropertyDescriptor;
                for(i$ = 0; i$ < len$; ++i$) {
                    PropertyDescriptor metohd = arr$[i$];
                    if(beanWrapper.isReadableProperty(metohd.getName())) {
                        entityPropertyDescriptor = new EntityPropertyDescriptor(metohd.getName(), metohd.getWriteMethod(), metohd.getReadMethod());
                        if(metohd.getReadMethod().isAnnotationPresent(Column.class)) {
                            Column column = (Column)metohd.getReadMethod().getAnnotation(Column.class);
                            entityPropertyDescriptor.setColumnName(column.name());
                            if(metohd.getReadMethod().isAnnotationPresent(Id.class)) {
                                entityPropertyDescriptor.setPrimaryKey(true);
                            }

                            if(metohd.getReadMethod().isAnnotationPresent(Version.class)) {
                                entityPropertyDescriptor.setVersion(true);
                            }
                        } else {
                            entityPropertyDescriptor.setTransient(true);
                        }

                        propertyDescriptors.add(entityPropertyDescriptor);
                    }
                }

                Method[] var9 = beanWrapper.getPropertyDescriptorsNotInDeclaringOrder();
                len$ = var9.length;

                for(i$ = 0; i$ < len$; ++i$) {
                    Method var10 = var9[i$];
                    entityPropertyDescriptor = new EntityPropertyDescriptor();
                    entityPropertyDescriptor.setOtherMethod(var10);
                    propertyDescriptors.add(entityPropertyDescriptor);
                }

                propertyDescriptorMap.put(clazz, propertyDescriptors);
                return (List)propertyDescriptorMap.get(clazz);
            }
        }

        public static synchronized <Entity extends BaseEntity> EntityPropertyDescriptor getPropertyDescriptorByFieldName(Class<Entity> clazz, String fieldName) {
            Iterator i$ = getEntityPropertyDescriptors(clazz).iterator();

            EntityPropertyDescriptor propertyDescriptor;
            do {
                if(!i$.hasNext()) {
                    return null;
                }

                propertyDescriptor = (EntityPropertyDescriptor)i$.next();
            } while(propertyDescriptor.getFieldName() == null || !propertyDescriptor.getFieldName().equals(fieldName));

            return propertyDescriptor;
        }

        public static synchronized <Entity extends BaseEntity> EntityPropertyDescriptor getPropertyDescriptorByColumnName(Class<Entity> clazz, String columnName) {
            Iterator i$ = getEntityPropertyDescriptors(clazz).iterator();

            EntityPropertyDescriptor propertyDescriptor;
            do {
                if(!i$.hasNext()) {
                    return null;
                }

                propertyDescriptor = (EntityPropertyDescriptor)i$.next();
            } while(propertyDescriptor.getColumnName() == null || !propertyDescriptor.getColumnName().equals(columnName));

            return propertyDescriptor;
        }

        public static synchronized <Entity extends BaseEntity> Method getMethodByMethodName(Class<Entity> clazz, String methodName) {
            Method method = null;
            Iterator i$ = getEntityPropertyDescriptors(clazz).iterator();

            while(i$.hasNext()) {
                EntityPropertyDescriptor propertyDescriptor = (EntityPropertyDescriptor)i$.next();
                if(propertyDescriptor.getGetMethod() != null && propertyDescriptor.getGetMethod().getName().equals(methodName)) {
                    method = propertyDescriptor.getGetMethod();
                }

                if(propertyDescriptor.getSetMethod() != null && propertyDescriptor.getSetMethod().getName().equals(methodName)) {
                    method = propertyDescriptor.getSetMethod();
                }

                if(propertyDescriptor.getOtherMethod() != null && propertyDescriptor.getOtherMethod().getName().equals(methodName)) {
                    method = propertyDescriptor.getOtherMethod();
                }
            }

            return method;
        }
    }
}
