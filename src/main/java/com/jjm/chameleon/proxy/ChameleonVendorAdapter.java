package com.jjm.chameleon.proxy;

import com.jjm.chameleon.factory.VendorProxyAdapterFactory;
import com.jjm.chameleon.init.PropertiesProcessor;
import com.jjm.chameleon.support.proxy.VendorProxyAdapter;
import org.springframework.util.StringUtils;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ChameleonVendorAdapter {

    private ChameleonVendorAdapter() {}

    private static ChameleonVendorAdapter instance;

    public static ChameleonVendorAdapter getInstance() {
        if (instance == null)
            instance = new ChameleonVendorAdapter();
        return instance;
    }

    public static VendorProxyAdapter create(Field field, String fieldName, Object data) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        VendorProxyAdapter defaultProxyChameleon = new DefaultProxyChameleon(field, fieldName, data);
        VendorProxyAdapter proxy = defaultProxyChameleon;
        if (proxy.getValue() != null) {
            try {
                String vendorAdapter = PropertiesProcessor.getInstance().getProperty(PropertiesProcessor.JPA_PROPERTIES_PROXY_VENDOR_ADAPTER);
                if (!StringUtils.isEmpty(vendorAdapter)) {
                    proxy = VendorProxyAdapterFactory.getInstance().create(vendorAdapter, proxy.getValue(), field, fieldName);
                    if (proxy.getClazz() == null || proxy.getValue() == null)
                        proxy = defaultProxyChameleon;
                }
            } catch (NoSuchMethodException | InvocationTargetException | FileNotFoundException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return proxy;
    }

}
