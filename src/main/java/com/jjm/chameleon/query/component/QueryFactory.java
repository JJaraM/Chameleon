package com.jjm.chameleon.query.component;

import com.jjm.chameleon.utils.StringUtils;
import java.util.List;

public class QueryFactory {

    private final static String SPACE = " ";
    private final static String SELECT = "SELECT";
    private final static String FROM = "FROM";
    private final static String JOIN = "JOIN";

    private static QueryFactory instance = null;

    private QueryFactory(){}


    public static QueryFactory getInstance() {
        if (instance == null)
            instance = new QueryFactory();
        return instance;
    }

    public Query create(String sql, Object source) {
        String columns = StringUtils.textBetweenWords(sql, SELECT,  FROM);
        String[] fromClause = getFromClause(sql).split(SPACE);
        Query query = new Query();
        Alias aliasQuery = query.select(columns).from(source).alias(fromClause[1]);
        List<String> joins = StringUtils.getClauses(sql, JOIN);
        for (String join : joins) {
            String[] joinClauses = join.split(SPACE);
            String clause = joinClauses[0];
            String alias = joinClauses[1];
            aliasQuery = aliasQuery.join(clause).alias(alias);
        }
        return query;
    }

    public static String getFromClause(String str) {
        List<String> from = StringUtils.getClauses(str, FROM);
        String table;
        if (str.contains(JOIN)) {
            table = from.get(0).substring(0, from.get(0).indexOf(JOIN)).trim();
        } else {
            table = from.get(0).trim();
        }
        return table;
    }

}
