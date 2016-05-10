package com.jjm.chameleon.query;

import com.jjm.chameleon.query.component.Query;

public interface QueryFacade {
    Object getData(Query query);
    String getAlias(Query query);
}
