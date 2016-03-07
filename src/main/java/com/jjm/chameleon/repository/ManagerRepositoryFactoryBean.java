/*
 * Copyright (c) 2016, 2030, JJM and/or its affiliates. All rights reserved.
 * JJM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jjm.chameleon.repository;

import com.jjm.chameleon.annotation.EnableChameleonRepository;
import com.jjm.chameleon.context.ChameleonApplication;
import com.jjm.chameleon.exceptions.AccessorException;
import com.jjm.chameleon.utils.ScannerUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.Set;

/**
 * Class that manage the all logic to create repository proxy instances
 */
public class ManagerRepositoryFactoryBean {

    /**
     * Run the process to create new proxy objects
     *
     * @param applicationContext used to register new proxy beans
     */
    public void execute(ApplicationContext applicationContext) {
        Class<?> mainClass = ChameleonApplication.getInstance().getMainClass();
        if (mainClass.isAnnotationPresent(EnableChameleonRepository.class)) {
            EnableChameleonRepository enableChameleonRepository = mainClass.getAnnotation(EnableChameleonRepository.class);
            String[] basePackages = enableChameleonRepository.basePackages();
            ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
            for (String basePackage : basePackages) {
                createProxyObjects(basePackage, beanFactory);
            }
        }
    }

    /**
     * Create proxy repository objects
     *
     * @param packageName to scan the interface classes
     * @param beanFactory used to register new beans
     */
    private void createProxyObjects(String packageName, ConfigurableListableBeanFactory beanFactory) {
        final Set<BeanDefinition> classes = ScannerUtils.scanInterfacesClasses(packageName);
        for (BeanDefinition bean: classes) {
            try {
                Class<?> clazz = Class.forName(bean.getBeanClassName());
                beanFactory.registerSingleton(clazz.getCanonicalName(), new ProxyRepository().getInstance(clazz));
            } catch (ClassNotFoundException e) {
                throw new AccessorException("There is not any class with the name " + bean.getBeanClassName(), e);
            }
        }
    }
}
