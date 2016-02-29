package com.jjm.chameleon.query;

public class ChameleonQueryAlias {

    private String alias;
    private ChameleonQueryJoin join;
    private ChameleonQueryJoinTables chameleonQueryJoinTables;

    public ChameleonQueryAlias() {
        chameleonQueryJoinTables = ChameleonQueryJoinTables.newInstance();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ChameleonQueryJoin join(String joinName) {
        join = new ChameleonQueryJoin();
        join.setJoin(joinName);
        chameleonQueryJoinTables.put(joinName.toLowerCase(), join);
        return chameleonQueryJoinTables.get(joinName.toLowerCase());
    }

    protected ChameleonQueryJoinTables joinTables() {
        return chameleonQueryJoinTables;
    }
}
