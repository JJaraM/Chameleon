package com.jjm.chameleon.service;

import java.util.Collection;

public class CollectionService {

    public static boolean isCollection(Class clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }
}
