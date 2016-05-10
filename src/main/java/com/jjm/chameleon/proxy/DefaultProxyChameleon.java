package com.jjm.chameleon.proxy;

import com.jjm.chameleon.annotation.ChameleonAttr;
import com.jjm.chameleon.support.proxy.VendorProxyAdapter;
import com.jjm.chameleon.utils.ReflectionUtils;
import java.lang.reflect.Field;

public class DefaultProxyChameleon implements VendorProxyAdapter {

    private Object value;
    private Class<?> clazz;
    private String fieldName;

    public DefaultProxyChameleon(Field field, String fieldName, Object data) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        value = ReflectionUtils.getFieldValue(fieldName, data);
        this.fieldName = fieldName;
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
    public String getFieldName() {
        return fieldName;
    }
}