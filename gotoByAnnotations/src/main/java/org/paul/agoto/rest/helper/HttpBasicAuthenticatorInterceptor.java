package org.paul.agoto.rest.helper;

import android.util.Log;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
@EBean(scope = EBean.Scope.Singleton)
public class HttpBasicAuthenticatorInterceptor implements ClientHttpRequestInterceptor {
    private static final String TAG="HttpBasicAuthenticator";
    @Bean
    AuthStore authStore;
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data, ClientHttpRequestExecution execution)
            throws IOException {
        // do something
        HttpHeaders headers = request.getHeaders();
        HttpAuthentication auth = new HttpBasicAuthentication(authStore.getUsername(), authStore.getPassword());
        headers.setAuthorization(auth);

        Log.d(TAG,data.toString());
        return execution.execute(request, data);
    }
}
