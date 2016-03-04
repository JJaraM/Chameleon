package com.jjm.chameleon.proxy;

import com.jjm.chameleon.support.proxy.VendorProxyAdapter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ChameleonVendorAdapterStrategy {

    public static VendorProxyAdapter getInstance(Field field, String fieldName, Object data) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        VendorProxyAdapter proxy = new DefaultProxyChameleon(field, fieldName, data);
        if (proxy.getValue() != null) {
            try {
                Class<?> clazz = Class.forName("com.jjm.chameleon.support.proxy.jpa.HibernateVendorProxyAdapter");
                Constructor<?> constructor = clazz.getConstructor(Object.class, Field.class);
                proxy = (VendorProxyAdapter) constructor.newInstance(new Object[] { proxy.getValue(), field });
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return proxy;
    }
}
