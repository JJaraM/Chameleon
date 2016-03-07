package com.jjm.chameleon.annotation;

import com.jjm.chameleon.utils.InterceptorSerializer;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Serializer {
    Class<? extends InterceptorSerializer> value();
}
