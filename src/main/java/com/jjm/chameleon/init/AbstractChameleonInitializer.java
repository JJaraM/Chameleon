package com.jjm.chameleon.init;

import com.jjm.chameleon.repository.ChameleonManagerRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.annotation.PostConstruct;

public class AbstractChameleonInitializer {

    @Autowired private ApplicationContext applicationContext;

    @PostConstruct
    public ChameleonManagerRepositoryFactoryBean getChameleonManagerFactoryBean() {
        ChameleonManagerRepositoryFactoryBean chameleonManagerFactoryBean = new ChameleonManagerRepositoryFactoryBean();
        chameleonManagerFactoryBean.execute(applicationContext);
        return chameleonManagerFactoryBean;
    }
}
