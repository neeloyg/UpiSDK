package com.upi.sdk.services;

import com.rssoftware.upiint.schema.ServiceRequest;
import com.rssoftware.upiint.schema.ServiceResponse;

import retrofit2.Call;

/**
 * Created by SwapanP on 26-04-2016.
 */
public abstract class ServiceIdentifier<T> {

    private final Class<T> typeClass;

    protected ServiceIdentifier(Class<T> typeClazz) {
        this.typeClass = typeClazz;
    }

    public Class<T> getTypeClass() {
        return typeClass;
    }

    public abstract Call<ServiceResponse> identifyService(T service, ServiceRequest request);
}
