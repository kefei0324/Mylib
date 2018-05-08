package org.paul.agoto.rest.helper;

import android.util.Log;
import org.androidannotations.annotations.EBean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
@EBean(scope = EBean.Scope.Singleton)
public class MyResponseErrorHandler implements ResponseErrorHandler {

    private static final String TAG= "MyResponseErrorHandler";

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        /**
         * 服务器响应本地验证逻辑..TODO
         */
        int rawStatusCode = clientHttpResponse.getRawStatusCode();

        return rawStatusCode==200;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        int rawStatusCode = clientHttpResponse.getRawStatusCode();
        String statusText = clientHttpResponse.getStatusText();
        Log.d(TAG, "[code="+String.valueOf(rawStatusCode)+"]");
        Log.d(TAG, "[msg="+statusText+"]");
    }
    public MyResponseErrorHandler() {}

}
