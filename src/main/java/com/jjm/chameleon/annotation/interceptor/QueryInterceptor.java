package com.jjm.chameleon.annotation.interceptor;

import com.jjm.chameleon.annotation.Query;
import com.jjm.chameleon.query.QueryManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@Aspect
@Component
public class QueryInterceptor {

    @Resource private QueryManager chameleonQueryManager;

    @Pointcut("execution(@com.jjm.chameleon.annotation.Query * *.*(..))")
    public void query(){}

    @Around("execution(* *(..)) && @annotation(query)")
    public Object execute(ProceedingJoinPoint jp, Query query) {
        Object[] args = jp.getArgs();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Type returnType = method.getGenericReturnType();
        Class<?> clazz = null;
        if (returnType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) returnType;
            Type[] argTypes = paramType.getActualTypeArguments();
            if (argTypes.length > 0) {
                clazz = (Class<?>) argTypes[0];
            }
        }
        return chameleonQueryManager.fetch(query.value(), args[0], clazz);
    }
}
