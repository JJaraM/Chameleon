package com.jjm.chameleon.query;


public class ChameleonQuerySelect {

    private String columns;
    private ChameleonQueryFrom chameleonQueryFrom;

    public ChameleonQuerySelect() {
        this.chameleonQueryFrom = new ChameleonQueryFrom();
    }

    public ChameleonQueryFrom from(Object object) {
        this.chameleonQueryFrom.from(object);
        return this.chameleonQueryFrom;
    }

    public ChameleonQueryFrom from() {
        return chameleonQueryFrom;
    }

    public ChameleonQuerySelect select(String columns) {
        this.columns = columns;
        return this;
    }

    public String columns() {
        return this.columns;
    }
}
