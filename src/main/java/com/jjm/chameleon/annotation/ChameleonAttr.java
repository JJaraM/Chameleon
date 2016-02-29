package com.jjm.chameleon.annotation;

import com.jjm.chameleon.annotation.jpa.ChameleonStrategy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChameleonAttr {
    String as() default "";
    Class<?> type() default void.class;
    ChameleonStrategy stretegy() default ChameleonStrategy.NONE;
}
