/*
 * Copyright (c) 2016, 2030, JJM and/or its affiliates. All rights reserved.
 * JJM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jjm.chameleon.utils;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * The scanner utils provide the common functionalities to access to the bean in some project using the package name
 */
public class ScannerUtils {

    /**
     * Scan the jars dependencies to find the classes in the desire package
     *
     * @param packageName package name to scan
     * @return a collection of bean definitions
     * @throws ClassNotFoundException
     */
    public static Set<BeanDefinition> scan(String packageName) throws ClassNotFoundException {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        return provider.findCandidateComponents(packageName);
    }

    /**
     * Scan the jars dependencies to find the classes that have the provide annotation in the provided package name
     *
     * @param packageName package name to scan
     * @param annotation annotation to scan in the interfaces
     * @return a collection of bean definitions
     */
    public static Set<BeanDefinition> scanInterfacesByAnnotation(String packageName, Annotation annotation) {
        return new AnnotationProvider(annotation).findCandidateComponents(packageName);
    }

    /**
     * Implementation used to found the beans that have an specific annotation
     */
    static class AnnotationProvider extends ClassPathScanningCandidateComponentProvider {
        public AnnotationProvider(Annotation annotation) {
            super(false);
            addIncludeFilter(new AnnotationTypeFilter(annotation.annotationType(), false));
        }

        /**
         * Override the method isCandidateComponent to validate only the interfaces clases
         * @param beanDefinition
         * @return
         */
        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return beanDefinition.getMetadata().isInterface();
        }
    }

}
