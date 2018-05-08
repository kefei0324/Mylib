package org.paul.agoto.manager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.paul.agoto.rest.RestClient;
import org.paul.agoto.rest.helper.MyErrorHandler;

@EBean(scope = EBean.Scope.Singleton)
public class RestManager {

    @AfterInject
    void afterInject() {
        restClient.setRestErrorHandler(myErrorHandler);
    }

    @Bean
    MyErrorHandler myErrorHandler;

    @RestService
    RestClient restClient;
}
