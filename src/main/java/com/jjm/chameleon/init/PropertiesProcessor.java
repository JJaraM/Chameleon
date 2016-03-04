package com.jjm.chameleon.init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProcessor {

    private static final String FILE_NAME = "application.properties";
    private static PropertiesProcessor propertiesProcessor;
    private static Properties properties;

    public static final String JPA_PROPERTIES_PROXY_VENDOR_ADAPTER = "chameleon.jpa.properties.vendor.proxy.adapter";

    public static PropertiesProcessor getInstance() {
        if (propertiesProcessor == null) {
            propertiesProcessor = new PropertiesProcessor();
        }
        return propertiesProcessor;
    }

    public Properties getProperties() throws FileNotFoundException {
        if (properties == null) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);
            if (inputStream != null) {
                try {
                    properties = new Properties();
                    properties.load(inputStream);
                } catch (IOException e) {
                    throw new FileNotFoundException("property file '" + FILE_NAME + "' not found in the classpath");
                }
            }
        }
        return properties;
    }


}
