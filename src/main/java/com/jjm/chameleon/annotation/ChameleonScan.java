package com.jjm.chameleon.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ChameleonScan {
    String[] basePackages();
}
