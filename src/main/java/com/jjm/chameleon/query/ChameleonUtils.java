package com.jjm.chameleon.query;

import com.jjm.chameleon.annotation.Chameleon;
import java.lang.annotation.Annotation;
import java.util.Set;

public class ChameleonUtils {

    public static boolean isChameleon(ChameleonQuery query, Object object) {
        Class<?> clazz = query.select().from().getObject().getClass();
        if (Set.class.isAssignableFrom(clazz)) {
            Set<?> set = (Set<?>) query.select().from().getObject();
            clazz = set.iterator().next().getClass();
        }
        Annotation annotation = ((Class) object).getAnnotation(Chameleon.class);
        Chameleon chameleon = (Chameleon) annotation;
        return clazz == chameleon.type();
    }

    public static boolean isChameleon(Class<?> clazz) {
        return clazz.isAnnotationPresent(Chameleon.class);
    }
}
