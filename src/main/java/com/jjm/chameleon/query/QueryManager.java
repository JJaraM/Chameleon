package com.jjm.chameleon.query;

import com.jjm.chameleon.query.component.Query;

import java.util.Map;

public interface QueryManager {
    <T> T fetch(Query query);
    <T> T fetch(Query query, Class<?> object);
    <T> T fetch(String sql, Object source, Class<?> clazz);
    <T> T fetch(String sql, Map<String, Object> params, Class<?> clazz);
}
