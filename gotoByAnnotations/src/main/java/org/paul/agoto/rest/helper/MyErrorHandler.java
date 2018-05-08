package org.paul.agoto.rest.helper;

import android.util.Log;
import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.api.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

@EBean(scope = EBean.Scope.Singleton)
public class MyErrorHandler implements RestErrorHandler {
    private static final String TAG="MyErrorHandler";
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e) {
        Log.e(TAG,e.getMessage());
    }
}
