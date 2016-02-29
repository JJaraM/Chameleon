package com.jjm.chameleon.proxy;

import com.jjm.chameleon.annotation.ChameleonAttr;
import com.jjm.chameleon.annotation.jpa.ChameleonStrategy;
import org.hibernate.collection.internal.PersistentSet;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public class PersistentSetProxy implements ChameleonProxy {

    private Object value;
    private Class<?> clazz;
    private ChameleonStrategy strategy;

    public PersistentSetProxy(Object object, Field field) {
        if (object instanceof PersistentSet) {
            PersistentSet proxy = (PersistentSet) object;
            Object[] array = proxy.toArray();
            if (array.length > 0) {
                Set<Object> set = new HashSet<>();
                for (Object arrayObject : array) {
                    set.add(arrayObject);
                }
                value = set;
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            }
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
