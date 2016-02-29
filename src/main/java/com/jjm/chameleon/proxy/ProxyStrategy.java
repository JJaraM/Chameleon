package com.jjm.chameleon.proxy;

import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.proxy.HibernateProxy;
import java.lang.reflect.Field;

public class ProxyStrategy {

    public static ChameleonProxy getInstance(Field field, String fieldName, Object data) throws InstantiationException, IllegalAccessException {
        ChameleonProxy proxy = new DefaultProxyChameleon(field, fieldName, data);
        if (proxy.getValue() != null) {
            if (proxy.getValue() instanceof HibernateProxy ) {
                proxy = new HibernateProxyChameleon(proxy.getValue(), field);
            } else if (proxy.getValue() instanceof PersistentSet) {
                proxy = new PersistentSetProxy(proxy.getValue(), field);
            }
        }
        return proxy;
    }
}
