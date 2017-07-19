package com.upi.sdk.services;

import java.util.List;

/**
 * Created by SwapanP on 22-04-2016.
 */
public abstract class ServiceCallback<T> {

    private final Class<T> typeClass;

    protected ServiceCallback(Class<T> typeClazz) {
        this.typeClass = typeClazz;
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }

    abstract public void onSuccess(T result);
    abstract public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors);
}
