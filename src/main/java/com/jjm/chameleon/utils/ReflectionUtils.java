/*
 * Copyright (c) 2016, 2030, JJM and/or its affiliates. All rights reserved.
 * JJM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jjm.chameleon.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.jjm.chameleon.annotation.Serializer;
import com.jjm.chameleon.exceptions.AccessorException;
import org.springframework.util.Assert;

/**
 * Utility class used to java reflection operations
 */
public class ReflectionUtils {

    /**
     * Get's the value by field name
     *
     * @param fieldName to search
     * @param data to extract the value
     * @return the value in the field
     */
    public static Object getFieldValue(String fieldName, Object data)  {
        Assert.notNull(fieldName, "field name must not be null");
        Assert.hasLength(fieldName, "field name must not be empty");
        Object object = null;
        if (data != null) {
            try {
                Field field = data.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                object = field.get(data);
            } catch (NoSuchFieldException e) {
                throw new AccessorException("There is not a field with the name " + fieldName, e);
            } catch (IllegalAccessException e) {
                throw new AccessorException("Illegal access in " + fieldName, e);
            }
        }
        return object;
    }

    /**
     * Create a new instance of a object
     *
     * @param object to be created
     * @return a new instance of a object
     */
    public static Object createInstance(Object object) {
        Assert.notNull(object, "object name must not be null");
        Object finalObject;
        try {
            finalObject = ((Class) object).newInstance();
        } catch (IllegalAccessException e) {
            throw new AccessorException("Illegal access in " + object, e);
        } catch (InstantiationException e) {
            throw new AccessorException("Error creating an instance of " + object, e);
        }
        return finalObject;
    }

    /**
     * Set a specific value using the field name
     *
     * @param fieldName to search the value
     * @param data to be set
     * @param field field to be used to set the new value
     * @param object that will have the new value
     */
    public static void setProperty(String fieldName, Object data, Field field, Object object) {
        Assert.notNull(fieldName, "field name must not be null");
        Assert.hasLength(fieldName, "field name must not be empty");
        Assert.notNull(field, "field must not be null");
        Assert.notNull(object, "object must not be null");
        Object value = getFieldValue(fieldName, data);;
        if (field.isAnnotationPresent(Serializer.class)) {
            Serializer serializer = field.getAnnotation(Serializer.class);
            Class<? extends InterceptorSerializer> clazz = serializer.value();
            try {
                InterceptorSerializer interceptorSerializer = clazz.newInstance();
                value = interceptorSerializer.getValue(value);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        setValueField(field, object, value);
    }

    /**
     * Set a specific field's value in the <code>object</code> param
     *
     * @param field field to be set
     * @param object object to set
     * @param objectReferenceValue value to be set
     */
    public static void setValueField(Field field, Object object, Object objectReferenceValue) {
        if (objectReferenceValue != null) {
            try {
                field.setAccessible(Boolean.TRUE);
                field.set(object, objectReferenceValue);
            } catch (IllegalAccessException e) {
                throw new AccessorException("Illegal access in " + field.getName(), e);
            }
        }
    }

    /**
     * <p> Get's the generic type in a method reference.
     * <p> For example:
     * <p> {@code Set<SomeObject>} getCollection();
     * <p> The current method will return an object of the generic type as {@code SomeObject}
     * @param method to get the generic class type
     * @return {@link Class} with the generic type
     */
    public static Class<?> getClassOfParametrizedType(Method method) {
        Assert.notNull(method, "method must not be null");
        Class<?> clazz = null;
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) returnType;
            Type[] argTypes = paramType.getActualTypeArguments();
            if (argTypes.length > 0) {
                clazz = (Class<?>) argTypes[0];
            }
        }
        return clazz;
    }
}
