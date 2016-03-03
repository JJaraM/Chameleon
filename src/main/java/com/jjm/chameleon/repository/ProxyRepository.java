package com.jjm.chameleon.repository;

import com.jjm.chameleon.annotation.Query;
import com.jjm.chameleon.query.ChameleonQueryManagerImpl;
import com.jjm.chameleon.utils.ReflectionUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import static java.lang.reflect.Proxy.newProxyInstance;

public class ProxyRepository {

    public <T> T getInstance(Class<T> clazz) {
        ProxyFactory proxyFactory = getProxyFactory(clazz);
        Object proxy = proxyFactory.getProxy();
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
            for (Annotation annotation : annotations) {
                if (annotation.annotationType() == Query.class) {
                    Query query = (Query) annotation;
                    Class<?> clazz = ReflectionUtils.getClassOfParametrizedType(method);
                    object = new ChameleonQueryManagerImpl().fetch(query.value(), args[0], clazz);
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
