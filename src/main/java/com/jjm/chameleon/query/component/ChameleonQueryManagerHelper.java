package com.jjm.chameleon.query.component;

import com.jjm.chameleon.annotation.ChameleonAttr;
import com.jjm.chameleon.context.ChameleonApplication;

import java.lang.reflect.Field;
import java.util.*;

public class ChameleonQueryManagerHelper {

    private final static String ALIAS_SEPARATOR = ".";
    private final static String COLUMN_SEPARATOR = ",";
    private final static String INCLUDE_ALL_COLUMNS = "*";

    private static Set<Class<?>> ret;
    private Set<String> columns;
    private JoinTables joinTables;

    public ChameleonQueryManagerHelper(Query query) {
        initializePrimitiveTypes();
        initializeColumns(query);
        initializeJoinTables(query);
    }

    private static void initializePrimitiveTypes() {
        if (ret == null) {
            ret = new HashSet<>();
            ret.add(Boolean.class);
            ret.add(Character.class);
            ret.add(Byte.class);
            ret.add(Short.class);
            ret.add(Integer.class);
            ret.add(Long.class);
            ret.add(Float.class);
            ret.add(Double.class);
            ret.add(String.class);
            ret.add(Date.class);
        }
    }

    public static Set<Class<?>> getPrimitiveTypes() {
        return ret;
    }

    private void initializeColumns(Query query) {
        columns = new HashSet<>();
        for(String column : query.select().columns().split(COLUMN_SEPARATOR)) {
            columns.add(column.trim());
        }
    }

    public Set<String> getColumns(){
        return columns;
    }

    private void initializeJoinTables(Query query) {
        joinTables = query.select().from().alias().joinTables();
    }

    public JoinTables getJoinTables() {
        return joinTables;
    }

    public static <T> Class<T> getFistReference(Query query) {
        Class<?> clazz = query.select().from().getObject().getClass();
        if (Set.class.isAssignableFrom(clazz)) {
            Set<?> set = (Set<?>) query.select().from().getObject();
            clazz = set.iterator().next().getClass();
        }
        return ChameleonApplication.getInstance().getContext().get(clazz);
    }

    public static String getFieldName(Field field) {
        String fieldName = field.getName().trim();
        if  (field.isAnnotationPresent(ChameleonAttr.class)) {
            ChameleonAttr chameleonAttr = field.getAnnotation(ChameleonAttr.class);
            fieldName = chameleonAttr.as().trim().length() > 0 ? chameleonAttr.as() : fieldName;
        }
        return fieldName;
    }

    public static String getFieldNameWithAlias(String alias, String fieldName) {
        return alias != null ? alias.trim().length() > 0 ? alias.trim().concat(ALIAS_SEPARATOR).concat(fieldName) : fieldName : fieldName;
    }

    public boolean existColumn(String field) {
        boolean includeColumn;
        if (getColumns().size() == 1 && getColumns().iterator().next().equals(INCLUDE_ALL_COLUMNS))
            includeColumn = true;
        else
            includeColumn = getColumns().contains(field);
        return includeColumn;
    }


    public String getAlias(String fieldName) {
        return existJoin(fieldName) ?  getJoinTables().get(fieldName).alias().getAlias() : null;
    }

    public boolean existJoin(final String fieldName) {
        return getJoinTables().get(fieldName) != null;
    }










}
