package com.jjm.chameleon.query;

public class ChameleonQuery {

    private ChameleonQuerySelect chameleonSelect;

    public ChameleonQuery() {
        this.chameleonSelect = new ChameleonQuerySelect();
    }

    public ChameleonQuerySelect select(String columns) {
        chameleonSelect.select(columns);
        return chameleonSelect;
    }

    protected ChameleonQuerySelect select() {
        return this.chameleonSelect;
    }
}
