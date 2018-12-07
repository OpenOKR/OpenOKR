package com.okr.framework.spring.beans;



import com.okr.framework.java.lang.reflect.ReflectionUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class BeanWrapper extends BeanWrapperImpl {
    public BeanWrapper(Object object) {
        super(object);
    }

    public BeanWrapper(Class<?> clazz) {
        super(clazz);
    }

    public PropertyDescriptor[] getPropertyDescriptorsInDeclaringOrder() {
        PropertyDescriptor[] propertyDescriptors = super.getPropertyDescriptors();
        List methodsInDeclaringOrder = ReflectionUtils.getMethodsInDeclaringOrder(this.getWrappedClass());
        LinkedHashSet resultPropertyDescriptors = new LinkedHashSet();
        Iterator i$ = methodsInDeclaringOrder.iterator();

        while(true) {
            while(i$.hasNext()) {
                Method method = (Method)i$.next();
                PropertyDescriptor[] arr$ = propertyDescriptors;
                int len$ = propertyDescriptors.length;

                for(int i$1 = 0; i$1 < len$; ++i$1) {
                    PropertyDescriptor propertyDescriptor = arr$[i$1];
                    if(method.equals(propertyDescriptor.getReadMethod()) || method.equals(propertyDescriptor.getWriteMethod())) {
                        resultPropertyDescriptors.add(propertyDescriptor);
                        break;
                    }
                }
            }

            return (PropertyDescriptor[])resultPropertyDescriptors.toArray(new PropertyDescriptor[resultPropertyDescriptors.size()]);
        }
    }

    public Method[] getPropertyDescriptorsNotInDeclaringOrder() {
        PropertyDescriptor[] propertyDescriptors = super.getPropertyDescriptors();
        List methodsInDeclaringOrder = ReflectionUtils.getMethodsInDeclaringOrder(this.getWrappedClass());
        LinkedHashSet otherMethod = new LinkedHashSet();
        boolean isSetMethodOrGetMethod = false;

        for(Iterator i$ = methodsInDeclaringOrder.iterator(); i$.hasNext(); isSetMethodOrGetMethod = false) {
            Method method = (Method)i$.next();
            PropertyDescriptor[] arr$ = propertyDescriptors;
            int len$ = propertyDescriptors.length;

            for(int i$1 = 0; i$1 < len$; ++i$1) {
                PropertyDescriptor propertyDescriptor = arr$[i$1];
                if(method.equals(propertyDescriptor.getReadMethod()) || method.equals(propertyDescriptor.getWriteMethod())) {
                    isSetMethodOrGetMethod = true;
                    break;
                }
            }

            if(!isSetMethodOrGetMethod) {
                otherMethod.add(method);
            }
        }

        return (Method[])otherMethod.toArray(new Method[otherMethod.size()]);
    }

    public void copyPropertiesTo(Object destinationBean, List<String> propertyNames) {
        BeanWrapper destinationBeanWrapper = new BeanWrapper(destinationBean);
        Iterator i$ = propertyNames.iterator();

        while(i$.hasNext()) {
            String propertyName = (String)i$.next();
            destinationBeanWrapper.setPropertyValue(propertyName, this.getPropertyValue(propertyName));
        }

    }

    public Object getPropertyValueRecursively(String propertyName) throws BeansException {
        int dotIndex = propertyName.indexOf(".");
        if(dotIndex == -1) {
            return this.getPropertyValue(propertyName);
        } else {
            Object propertyBean = this.getPropertyValue(propertyName.substring(0, dotIndex));
            return propertyBean == null?null:(new BeanWrapper(propertyBean)).getPropertyValueRecursively(propertyName.substring(dotIndex + 1));
        }
    }

    public Class<?> getPropertyTypeRecursively(String propertyName) throws BeansException {
        int dotIndex = propertyName.indexOf(".");
        if(dotIndex == -1) {
            return this.getPropertyType(propertyName);
        } else {
            Object propertyBean = this.getPropertyValue(propertyName.substring(0, dotIndex));
            return propertyBean == null?null:(new BeanWrapper(propertyBean)).getPropertyTypeRecursively(propertyName.substring(dotIndex + 1));
        }
    }
}

