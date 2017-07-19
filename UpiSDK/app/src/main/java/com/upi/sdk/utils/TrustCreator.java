package com.upi.sdk.utils;


import android.util.Base64;
import android.util.Log;

import com.upi.sdk.CryptLib;

/**
 * Created by SwapanP on 10-02-2016.
 */
public class TrustCreator {

    public static String createTrust(String message, String token) {
        String trust = null;

        CryptLib lib = null;
        try {
            lib = new CryptLib();
            Log.d("Trust Creator Token", token);
            Log.d("CL Trust Param Message", message);
            byte[] tokenBytes = lib.hexStringToByteArray(token);
            //String trustHash = lib.SHA256(message);
            //Log.d("Trust Creator hash", trustHash);
            trust = Base64.encodeToString(lib.encrypt(lib.SHA256(message), tokenBytes), Base64.NO_WRAP);
            Log.d("trust", trust);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return trust;
    }
}


