package com.jjm.chameleon.repository;

import com.jjm.chameleon.annotation.Datasource;
import com.jjm.chameleon.annotation.Param;
import com.jjm.chameleon.annotation.Query;
import com.jjm.chameleon.query.QueryManagerImpl;
import com.jjm.chameleon.utils.ReflectionUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.reflect.Proxy.newProxyInstance;

public class ProxyRepository {

    public <T> T getInstance(Class<T> clazz) {
        Object proxy = getProxyFactory(clazz).getProxy();
        T instance = null;
        if (proxy != null) {
            instance = (T) proxy;
        }
        return instance;
    }

    private <T> ProxyFactory getProxyFactory(Class<T> clazz) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(new SimpleStaticPointcut(), new SimpleAdvice()));
        proxyFactory.setTarget(newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },(proxy, m, args) -> getObject(m, args)));
        return proxyFactory;
    }

    public Object getObject(Method method, Object args[]) {
        Object object = null;
        try {
            Annotation[] annotations = method.getAnnotations();
            Parameter[] parameters = method.getParameters();
            Map<String, Object> params = new HashMap<>();
            for (int i=0; i< parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(Datasource.class)) {
                    params.put("DATA_SOURCE", args[i]);
                } else if (parameter.isAnnotationPresent(Param.class)) {
                    Param param = parameter.getAnnotation(Param.class);
                    params.put(param.value(), args[i]);
                }
            }
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Query.class) {
                    Query query = (Query) annotation;
                    Class<?> clazz = ReflectionUtils.getClassOfParametrizedType(method);
                    if (clazz == null) {
                        clazz = method.getReturnType();
                    }
                    object = new QueryManagerImpl().fetch(query.value(), params, clazz);
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        }
        return object;
    }

    class SimpleAdvice implements MethodInterceptor {
        public Object invoke(MethodInvocation invocation) throws Throwable {
            return invocation.proceed();
        }
    }

    class SimpleStaticPointcut extends StaticMethodMatcherPointcut {
        public boolean matches(Method method, Class cls) {
            return true;
        }
        public ClassFilter getClassFilter() {
            return cls -> true;
        }
    }
}
