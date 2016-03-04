package com.jjm.chameleon.query.component;

public class From {

    private Object object;
    private Alias alias;

    public From() {
        this.alias = new Alias();
    }

    public void from(Object object) {
        this.object = object;
    }

    protected Alias alias(String alias) {
        this.alias.setAlias(alias);
        return this.alias;
    }

    public Alias alias() {
        return this.alias;
    }

    public Object getObject() {
        return object;
    }


}
