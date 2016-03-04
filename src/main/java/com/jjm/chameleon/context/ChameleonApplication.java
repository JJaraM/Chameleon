package com.jjm.chameleon.context;

import com.jjm.chameleon.annotation.Chameleon;
import com.jjm.chameleon.annotation.ChameleonScan;
import com.jjm.chameleon.query.component.ChameleonUtils;
import com.jjm.chameleon.utils.ScannerUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChameleonApplication {

    private static ChameleonApplication instance;
    private static Map<Class, Class> map = new HashMap<>();
    private static Class<?> mainClass;

    private ChameleonApplication(){}

    public static void run(Class<?> clazz) {
        ChameleonApplication applicationContext = getInstance();
        applicationContext.setMainClass(clazz);

        if (clazz.isAnnotationPresent(ChameleonScan.class)) {
            ChameleonScan chameleonScan = clazz.getAnnotation(ChameleonScan.class);
            String[] basePackages = chameleonScan.basePackages();
            for (String basePackage : basePackages) {
                try {
                    applicationContext.scan(basePackage);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ChameleonApplication getInstance() {
        if (instance == null) {
            instance = new ChameleonApplication();
        }
        return  instance;
    }

    private void setMainClass(Class<?> clazz) {
        mainClass = clazz;
    }

    public Class<?> getMainClass() {
        return mainClass;
    }

    public void scan(String packageName) throws ClassNotFoundException {
        final Set<BeanDefinition> classes = ScannerUtils.scan(packageName);
        for (BeanDefinition bean: classes) {
            Class<?> clazz = Class.forName(bean.getBeanClassName());
            if (ChameleonUtils.isChameleon(clazz)) {
                Chameleon chameleon = clazz.getAnnotation(Chameleon.class);
                map.put(chameleon.type(), clazz);
            }
        }
    }

    public Map<Class, Class> getContext() {
        return map;
    }

}
