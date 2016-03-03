package com.jjm.chameleon.annotation;

import org.springframework.stereotype.Component;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface Repository {

}
