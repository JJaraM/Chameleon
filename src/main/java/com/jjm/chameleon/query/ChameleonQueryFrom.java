package com.jjm.chameleon.query;

public class ChameleonQueryFrom {

    private Object object;
    private ChameleonQueryAlias alias;

    public ChameleonQueryFrom() {
        this.alias = new ChameleonQueryAlias();
    }

    public void from(Object object) {
        this.object = object;
    }

    public ChameleonQueryAlias alias(String alias) {
        this.alias.setAlias(alias);
        return this.alias;
    }

    protected ChameleonQueryAlias alias() {
        return this.alias;
    }

    public Object getObject() {
        return object;
    }


}
