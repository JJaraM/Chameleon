package com.jjm.chameleon.proxy;

import com.jjm.chameleon.annotation.jpa.ChameleonStrategy;
import org.hibernate.proxy.HibernateProxy;
import java.lang.reflect.Field;

public class HibernateProxyChameleon implements ChameleonProxy {

    private Object value;
    private Class<?> clazz;
    private ChameleonStrategy strategy;

    public HibernateProxyChameleon(Object object, Field field) {
        HibernateProxy proxy = (HibernateProxy) object;
        value = proxy.getHibernateLazyInitializer().getImplementation();
        clazz = field.getType();
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