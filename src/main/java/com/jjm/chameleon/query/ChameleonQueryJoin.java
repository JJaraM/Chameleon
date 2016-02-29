package com.jjm.chameleon.query;

public class ChameleonQueryJoin {

    private String joinName;
    private ChameleonQueryAlias chameleonAlias;

    public ChameleonQueryJoin() {
        chameleonAlias = new ChameleonQueryAlias();
    }

    public void setJoin(String joinName) {
        this.joinName = joinName;
    }

    public ChameleonQueryAlias alias(String alias) {
        chameleonAlias.setAlias(alias);
        return chameleonAlias;
    }

    protected ChameleonQueryAlias alias() {
        return chameleonAlias;
    }
}
