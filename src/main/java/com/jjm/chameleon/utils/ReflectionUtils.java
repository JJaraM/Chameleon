package com.jjm.chameleon.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static Object getFieldValue(String fieldName, Object data) throws InstantiationException, IllegalAccessException {
        Object object = null;
        if (data != null) {
            Field[] fields = data.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    try {
                        object = field.get(data);
                    } catch (Exception e) {

                    }
                    return object;
                }
            }
        }
        return object;
    }

    public static Object createInstance(Object object) {
        Object finalObject = null;
        try {
            finalObject = ((Class) object).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return finalObject;
    }

    public static void setProperty(String fieldName, Object data, Field field, Object object) throws IllegalAccessException, InstantiationException {
        Object objectReferenceValue = getFieldValue(fieldName, data);
        setValueField(field, object, objectReferenceValue);
    }

    public static void setValueField(Field field, Object object, Object objectReferenceValue) throws IllegalAccessException {
        if (objectReferenceValue != null) {
            field.setAccessible(Boolean.TRUE);
            field.set(object, objectReferenceValue);
        }
    }

}
