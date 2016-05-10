package com.jjm.chameleon.query.component;

public class Alias {

    private String alias;
    private Join join;
    private JoinTables chameleonQueryJoinTables;

    public Alias() {
        chameleonQueryJoinTables = JoinTables.newInstance();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Join join(String joinName) {
        join = new Join();
        join.setJoin(joinName);
        chameleonQueryJoinTables.put(joinName, join);
        return join;
    }

    protected JoinTables joinTables() {
        return chameleonQueryJoinTables;
    }
}
