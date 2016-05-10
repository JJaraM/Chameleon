package com.jjm.chameleon.query;

import com.jjm.chameleon.query.component.Query;

public class QueryFacadeImpl implements QueryFacade {

    @Override
    public Object getData(Query query) {
        return query.select().from().getObject();
    }

    @Override
    public String getAlias(Query query) {
        return query.select().from().alias().getAlias();
    }

}
