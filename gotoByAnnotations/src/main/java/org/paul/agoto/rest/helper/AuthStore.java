package org.paul.agoto.rest.helper;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class AuthStore {
    public String getUsername() {
        return "H2G2";
    }

    public String getPassword() {
        return "42";
    }
}
