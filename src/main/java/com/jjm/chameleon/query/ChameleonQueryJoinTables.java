package com.jjm.chameleon.query;

import java.util.HashMap;
import java.util.Map;

public class ChameleonQueryJoinTables {

    private Map<String, ChameleonQueryJoin> joinTable;
    private static ChameleonQueryJoinTables instance;

    private ChameleonQueryJoinTables() {
        joinTable = new HashMap<>();
    }

    public static ChameleonQueryJoinTables newInstance() {
        if (instance == null) {
            instance = new ChameleonQueryJoinTables();
        }
        return instance;
    }

    public static ChameleonQueryJoinTables getInstance() {
        return instance;
    }

    public void put(String instanceName, ChameleonQueryJoin queryJoin) {
        joinTable.put(instanceName.toLowerCase(), queryJoin);
    }

    public ChameleonQueryJoin get(String instanceName){
        return joinTable.get(instanceName);
    }

    public boolean isEmpty() {
        return joinTable.isEmpty();
    }

}
