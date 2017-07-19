package com.upi.sdk.errors;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class ConversionException extends Exception {

    private SDKErrorCodes errorCode;

    public SDKErrorCodes getErrorCode(){
        return this.errorCode;
    }

    public ConversionException(SDKErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public ConversionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ConversionException(String detailMessage) {
        super(detailMessage);
    }



}
