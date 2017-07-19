package com.upi.sdk.errors;

/**
 * Created by SwapanP on 22-04-2016.
 */
public class UPISDKException extends Exception {

    private SDKErrorCodes errorCode;

    public UPISDKException(SDKErrorCodes errorCode) {
        this.errorCode = errorCode;
    }

    public static SDKErrorCodes getError(String ErrorCode) {
        SDKErrorCodes code = null;
        switch (ErrorCode) {
            case "ERR00013":
                code = SDKErrorCodes.ERR00013;
                break;
            case "ERR00014":
                code = SDKErrorCodes.ERR00014;
                break;
            case "ERR00015":
                code = SDKErrorCodes.ERR00015;
                break;
            case "ERR00047":
                code = SDKErrorCodes.ERR00047;
                break;
            case "ERR00048":
                code = SDKErrorCodes.ERR00048;
                break;
            case "ERR00025":
                code = SDKErrorCodes.ERR00025;
                break;
            case "ERR00023":
                code = SDKErrorCodes.ERR00023;
                break;
            case "ERR00050":
                code = SDKErrorCodes.ERR00050;
                break;
            case "ERR00017":
                code = SDKErrorCodes.ERR00017;
                break;
            case "ERR00018":
                code = SDKErrorCodes.ERR00018;
                break;
            case "ERR00020":
                code = SDKErrorCodes.ERR00020;
                break;
            case "ERR00021":
                code = SDKErrorCodes.ERR00021;
                break;
            case "ERR00098":
                code = SDKErrorCodes.ERR00098;
                break;
            case "ERR00000":
                code = SDKErrorCodes.ERR00000;
                break;
            default:
                code = SDKErrorCodes.ERR00000;
                break;
        }

        return code;
    }
}
