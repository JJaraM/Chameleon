package com.jjm.chameleon.query.component;

import java.util.HashMap;
import java.util.Map;

public class JoinTables {

    private Map<String, Join> joinTable;
    private static JoinTables instance;

    private JoinTables() {
        joinTable = new HashMap<>();
    }

    public static JoinTables newInstance() {
        if (instance == null) {
            instance = new JoinTables();
        }
        return instance;
    }

    public static JoinTables getInstance() {
        return instance;
    }

    public void put(String instanceName, Join queryJoin) {
        joinTable.put(instanceName.toLowerCase(), queryJoin);
    }

    public Join get(String instanceName){
        return joinTable.get(instanceName);
    }

    public boolean isEmpty() {
        return joinTable.isEmpty();
    }

}
