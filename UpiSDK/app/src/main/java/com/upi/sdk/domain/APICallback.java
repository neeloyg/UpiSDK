package com.upi.sdk.domain;

/**
 * Created by SwapanP on 22-04-2016.
 */
public interface APICallback<T> {

    void onSuccess(T result);
    void onFailure(String SdkErrorCode,String UpiErrorCode);

}
