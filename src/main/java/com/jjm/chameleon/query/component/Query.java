package com.jjm.chameleon.query.component;

public class Query {

    private Select chameleonSelect;

    public Query() {
        this.chameleonSelect = new Select();
    }

    protected Select select(String columns) {
        chameleonSelect.select(columns);
        return chameleonSelect;
    }

    public Select select() {
        return this.chameleonSelect;
    }
}
