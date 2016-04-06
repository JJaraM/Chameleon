package com.jjm.chameleon.proxy;

import com.jjm.chameleon.exceptions.MissingDependencyException;
import com.jjm.chameleon.init.PropertiesProcessor;
import com.jjm.chameleon.support.proxy.VendorProxyAdapter;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ChameleonVendorAdapterStrategy {

    public static VendorProxyAdapter getInstance(Field field, String fieldName, Object data) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        VendorProxyAdapter defaultProxyChameleon = new DefaultProxyChameleon(field, fieldName, data);
        VendorProxyAdapter proxy = defaultProxyChameleon;
        if (proxy.getValue() != null) {
            try {
                String vendorAdapter = PropertiesProcessor.getInstance().getProperties().getProperty(PropertiesProcessor.JPA_PROPERTIES_PROXY_VENDOR_ADAPTER);
                if (vendorAdapter != null && vendorAdapter.trim().length() > 0) {
                    try {
                        Class<?> clazz = Class.forName(vendorAdapter);
                        Constructor<?> constructor = clazz.getConstructor(Object.class, Field.class);
                        proxy = (VendorProxyAdapter) constructor.newInstance(new Object[] { proxy.getValue(), field });
                        if (proxy.getClazz() == null || proxy.getValue() == null) {
                            proxy = defaultProxyChameleon;
                        }
                    } catch (ClassNotFoundException e) {
                       throw new MissingDependencyException("Missing " +
                               "com.jjm:chameleon-jpa-support-hibernate" +
                               " dependency", e);
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return proxy;
    }
}
