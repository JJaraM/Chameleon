package com.jjm.chameleon.query;

import java.lang.reflect.Field;
import com.jjm.chameleon.factory.CollectionFactory;
import com.jjm.chameleon.query.component.Query;
import com.jjm.chameleon.query.component.ChameleonQueryManagerHelper;
import com.jjm.chameleon.query.component.ChameleonUtils;
import com.jjm.chameleon.query.component.QueryFactory;
import com.jjm.chameleon.service.CollectionService;
import com.jjm.chameleon.support.proxy.VendorProxyAdapter;
import com.jjm.chameleon.utils.ReflectionUtils;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import com.jjm.chameleon.proxy.*;

@Component
public class QueryManagerImpl implements QueryManager {

    private ChameleonQueryManagerHelper helper;
    private QueryFacade queryFacade = new QueryFacadeImpl();

    @Override
    public <T> T fetch(Query query) {
        return fetch(query, null);
    }

    @Override
    public <T> T fetch(Query query, Class<?> clazz) {
        AtomicReference<T> atomicReference = new AtomicReference<>();
        try {
            clazz = getClazz(clazz, query);
            if (ChameleonUtils.isChameleon(query, clazz)) {
                helper = new ChameleonQueryManagerHelper(query);
                Object object = createObject(queryFacade.getAlias(query), queryFacade.getData(query), clazz);
                if (object != null)
                    atomicReference.set((T) object);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException e) {
           System.err.println(e.getMessage());
        }
        return atomicReference.get();
    }

    public <T> T fetch(String sql, Object source, Class<?> clazz) {
        return fetch(QueryFactory.getInstance().create(sql, source), clazz);
    }

    private Class getClazz(Class clazz, Query query) {
        if (clazz == null)
            clazz = ChameleonQueryManagerHelper.getFistReference(query);
        return clazz;
    }

    @Override
    public <T> T fetch(String sql, Map<String, Object> params, Class<?> clazz) {
        return fetch(sql, params.get("DATA_SOURCE"), clazz);
    }


    private <T> Object createObject(String alias, Object data, Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Object object = getObject(alias, data, clazz);
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = ChameleonQueryManagerHelper.getFieldName(field);
            if (!isPrimitive(field) && helper.existJoin(fieldName)) {
                setObjectRelations(fieldName, data, field, object);
            } else if (existColumn(alias, fieldName)) {
                ReflectionUtils.setProperty(fieldName, data, field, object);
            }
        }
        return object;
    }

    private boolean isPrimitive(final Field field) {
        return ChameleonQueryManagerHelper.getPrimitiveTypes().contains(field.getType());
    }

    private boolean existColumn(final String alias, final String fieldName) {
        return helper.existColumn(ChameleonQueryManagerHelper.getFieldNameWithAlias(alias, fieldName));
    }

    private Object getObject(String alias, Object data, Class clazz) throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Object object;
        if (CollectionService.isCollection(data.getClass()))
            object = createCollection(alias, (Collection<Object>) data, clazz);
        else
            object = ReflectionUtils.createInstance(clazz);
        return object;
    }

    private void setObjectRelations(String fieldName, Object data, Field field, Object object) throws IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException {
        VendorProxyAdapter proxy = ChameleonVendorAdapter.getInstance().create(field, fieldName, data);
        if (proxy.getValue() != null && proxy.getClazz() != Void.TYPE)
            ReflectionUtils.setValueField(field, object, getObjectRelationInstance(proxy));
    }

    private Object getObjectRelationInstance(final VendorProxyAdapter proxy) throws IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException {
        Object newInstance;
        String alias = helper.getAlias(proxy.getFieldName());
        if (CollectionService.isCollection(proxy.getValue().getClass()))
            newInstance = createCollection(alias, (Collection<Object>) proxy.getValue(), proxy.getClazz());
        else
            newInstance = createObject(alias, proxy.getValue(), proxy.getClazz());
        return newInstance;
    }

    public Collection<Object> createCollection(String alias, Collection<Object> items, Class clazz) throws NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Collection collection = CollectionFactory.getInstance().create(items);
        for (Object item : items)
            collection.add(createObject(alias, item, clazz));
        return collection;
    }

}
