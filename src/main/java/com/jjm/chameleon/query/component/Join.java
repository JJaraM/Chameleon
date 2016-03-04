package com.jjm.chameleon.query.component;

public class Join {

    private String joinName;
    private Alias chameleonAlias;

    public Join() {
        chameleonAlias = new Alias();
    }

    public void setJoin(String joinName) {
        this.joinName = joinName;
    }

    public Alias alias(String alias) {
        chameleonAlias.setAlias(alias);
        return chameleonAlias;
    }

    protected Alias alias() {
        return chameleonAlias;
    }
}
