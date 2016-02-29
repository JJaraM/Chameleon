package com.jjm.chameleon.query;

public interface ChameleonQueryManager {
    <T> T fetch(ChameleonQuery query);
    <T> T fetch(ChameleonQuery query, Class<?> object);
    <T> T fetch(String sql, Object source, Class<?> clazz);
}
