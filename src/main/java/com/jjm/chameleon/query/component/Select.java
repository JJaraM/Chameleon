package com.jjm.chameleon.query.component;


public class Select {

    private String columns;
    private From chameleonQueryFrom;

    public Select() {
        this.chameleonQueryFrom = new From();
    }

    public From from(Object object) {
        this.chameleonQueryFrom.from(object);
        return this.chameleonQueryFrom;
    }

    public From from() {
        return chameleonQueryFrom;
    }

    public Select select(String columns) {
        this.columns = columns;
        return this;
    }

    public String columns() {
        return this.columns;
    }
}
