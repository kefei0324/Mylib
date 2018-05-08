package org.paul.agoto.rest;

import org.androidannotations.rest.spring.annotations.*;
import org.androidannotations.rest.spring.api.MediaType;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.paul.agoto.rest.helper.HttpBasicAuthenticatorInterceptor;
import org.paul.agoto.rest.helper.HttpsClientRequestFactory;
import org.paul.agoto.rest.helper.MyResponseErrorHandler;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Map;

import static org.paul.agoto.BuildConfig.API_HOST;


@Rest(rootUrl = API_HOST,
        converters =
                {StringHttpMessageConverter.class,
                        MappingJackson2HttpMessageConverter.class},
        requestFactory = HttpsClientRequestFactory.class,
        responseErrorHandler = MyResponseErrorHandler.class,
        interceptors = {HttpBasicAuthenticatorInterceptor.class})
public interface RestClient extends RestClientErrorHandling {


    @Post("/account/account/{version}/login")
    @Accept(MediaType.APPLICATION_JSON)
    String login(@Path String version, @Body Map<String, Object> loginInfo);


}