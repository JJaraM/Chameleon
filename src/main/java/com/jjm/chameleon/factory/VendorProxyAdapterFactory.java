package com.jjm.chameleon.factory;

import com.jjm.chameleon.support.proxy.VendorProxyAdapter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class VendorProxyAdapterFactory {

    private static VendorProxyAdapterFactory instance = null;

    private VendorProxyAdapterFactory(){}

    public static VendorProxyAdapterFactory getInstance() {
        if (instance == null)
            instance = new VendorProxyAdapterFactory();
        return instance;
    }

    public VendorProxyAdapter create(String vendorAdapter, Object value, Field field, String fieldName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName(vendorAdapter);
        Constructor<?> constructor = clazz.getConstructor(Object.class, Field.class, String.class);
        return (VendorProxyAdapter) constructor.newInstance(new Object[] { value, field, fieldName });
    }

}
