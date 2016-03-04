package com.jjm.chameleon.query;

import java.lang.reflect.Field;
import com.jjm.chameleon.query.component.Query;
import com.jjm.chameleon.query.component.ChameleonQueryManagerHelper;
import com.jjm.chameleon.query.component.ChameleonUtils;
import com.jjm.chameleon.support.proxy.VendorProxyAdapter;
import com.jjm.chameleon.utils.ReflectionUtils;
import org.springframework.stereotype.Component;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import com.jjm.chameleon.proxy.*;

@Component
public class QueryManagerImpl implements QueryManager {

    private ChameleonQueryManagerHelper helper;

    @Override
    public <T> T fetch(Query query) {
        return fetch(query, null);
    }

    @Override
    public <T> T fetch(Query query, Class<?> clazz) {
        AtomicReference<T> atomicReference = new AtomicReference<>();
        try {
            if (clazz == null) {
                clazz = ChameleonQueryManagerHelper.getFistReference(query);
            }
            if (ChameleonUtils.isChameleon(query, clazz)) {
                helper = new ChameleonQueryManagerHelper(query);
                Object data = query.select().from().getObject();
                String alias = query.select().from().alias().getAlias();
                Object object = createObject(alias, data, clazz);
                if (object != null) {
                    atomicReference.set((T) object);
                }
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | NoSuchFieldException e) {
           System.err.println(e.getMessage());
        }
        return atomicReference.get();
    }

    public <T> T fetch(String sql, Object source, Class<?> clazz) {
        return fetch(ChameleonQueryManagerHelper.createQueryManager(sql, source), clazz);
    }

    private <T> Object createObject(String alias, Object data, Class<T> clazz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Object object;
        if (data != null && Set.class.isAssignableFrom(data.getClass())) {
            Set<Object> items = (Set<Object>) data;
            object = createSetCollection(items, alias, clazz);
        } else {
            object = ReflectionUtils.createInstance(clazz);
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = ChameleonQueryManagerHelper.getFieldName(field);
                if (!ChameleonQueryManagerHelper.getPrimitiveTypes().contains(field.getType()) && helper.getJoinTables().get(fieldName) != null) {
                    setObjectRelations(field, fieldName, data, object);
                } else if (helper.existColumn(ChameleonQueryManagerHelper.getFieldNameWithAlias(alias, fieldName))) {
                    ReflectionUtils.setProperty(fieldName, data, field, object);
                }
            }
        }
        return object;
    }

    private void setObjectRelations(Field field, String fieldName, Object data, Object object) throws IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException {
        VendorProxyAdapter proxy = ChameleonVendorAdapterStrategy.getInstance(field, fieldName, data);
        if (proxy.getValue() != null && proxy.getClazz() != Void.TYPE) {
            ReflectionUtils.setValueField(field, object, getObjectRelationInstance(proxy, fieldName));
        }
    }

    private Object getObjectRelationInstance(VendorProxyAdapter proxy, String fieldName) throws IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException {
        Object newInstance;
        if (proxy.getValue() instanceof Set) {
            newInstance = createSetCollection(proxy.getValue(), helper.getAlias(fieldName), proxy.getClazz());
        } else {
            newInstance = createObject(helper.getAlias(fieldName), proxy.getValue(), proxy.getClazz());
        }
        return newInstance;
    }

    private <T> Set<Object> createSetCollection(Object items, String alias, Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, NoSuchFieldException {
        Set<Object> result = new LinkedHashSet<>();
        for (Object item : (Set) items) {
            result.add(createObject(alias, item, clazz));
        }
        return result;
    }

}
