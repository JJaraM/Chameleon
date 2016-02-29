package com.jjm.chameleon.proxy;

import com.jjm.chameleon.annotation.ChameleonAttr;
import com.jjm.chameleon.annotation.jpa.ChameleonStrategy;
import com.jjm.chameleon.utils.ReflectionUtils;

import java.lang.reflect.Field;

public class DefaultProxyChameleon implements ChameleonProxy {

    private Object value;
    private Class<?> clazz;
    private ChameleonStrategy strategy;

    public DefaultProxyChameleon(Field field, String fieldName, Object data) throws IllegalAccessException, InstantiationException {
        value = ReflectionUtils.getFieldValue(fieldName, data);
        if (field.isAnnotationPresent(ChameleonAttr.class)) {
            clazz = field.getAnnotation(ChameleonAttr.class).type();
        } else {
            clazz = field.getType();
        }
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public ChameleonStrategy getStrategy() {
        return strategy;
    }
}