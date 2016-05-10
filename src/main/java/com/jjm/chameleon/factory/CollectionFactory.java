package com.jjm.chameleon.factory;

import java.util.*;

public class CollectionFactory {

    private CollectionFactory(){}

    private static CollectionFactory instance;

    public static CollectionFactory getInstance(){
        if (instance == null)
            instance = new CollectionFactory();
        return instance;
    }

    public Collection create(Collection collection) {
        Collection instance = null;
        if (collection instanceof Set)
            instance = new HashSet<>();
        if (collection instanceof List)
            instance = new ArrayList<>();
        return instance;
    }
}
