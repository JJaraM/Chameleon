package com.jjm.chameleon.utils;

import java.util.Set;

public class SetCollections {

    public static <T> T get(Set<T> items, int index) {
        int i = 0;
        for (Object item : items) {
            if (index == i) {
                return (T) item;
            }
            i++;
        }
        return null;
    }
}
