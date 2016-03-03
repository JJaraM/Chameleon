package com.jjm.chameleon.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableChameleonRepository {
    String[] basePackages() default {};
}
