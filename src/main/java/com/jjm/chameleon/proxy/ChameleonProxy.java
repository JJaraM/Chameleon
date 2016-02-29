package com.jjm.chameleon.proxy;

import com.jjm.chameleon.annotation.jpa.ChameleonStrategy;

public interface ChameleonProxy {
    Class<?> getClazz();
    Object getValue();
    ChameleonStrategy getStrategy();
}