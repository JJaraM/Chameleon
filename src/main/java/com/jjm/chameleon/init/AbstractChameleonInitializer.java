package com.jjm.chameleon.init;

import com.jjm.chameleon.repository.ManagerRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import javax.annotation.PostConstruct;

public class AbstractChameleonInitializer {

    @Autowired private ApplicationContext applicationContext;

    @PostConstruct
    public ManagerRepositoryFactoryBean getChameleonManagerFactoryBean() {
        ManagerRepositoryFactoryBean chameleonManagerFactoryBean = new ManagerRepositoryFactoryBean();
        chameleonManagerFactoryBean.execute(applicationContext);
        return chameleonManagerFactoryBean;
    }
}
