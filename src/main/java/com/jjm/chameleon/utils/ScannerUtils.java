package com.jjm.chameleon.utils;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import java.util.Set;
import java.util.regex.Pattern;

public class ScannerUtils {

    public static Set<BeanDefinition> scan(String packageName) throws ClassNotFoundException {
        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
        return provider.findCandidateComponents(packageName);
    }

}
