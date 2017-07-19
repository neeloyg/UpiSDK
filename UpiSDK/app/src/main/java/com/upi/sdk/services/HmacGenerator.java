package com.upi.sdk.services;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.rssoftware.upiint.schema.PaymentRequest;
import com.upi.sdk.CryptLib;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.utils.SDKUtils;

import java.security.PublicKey;
import java.text.DecimalFormat;

/**
 * Created by SwapanP on 24-05-2016.
 */
public class HmacGenerator {

    private String K0 = "";
    private CryptLib cryptLib = new CryptLib();
   // private DatabaseHelper dbHelper;

    public String generateHMAC(HmacGenerationPolicy hmacPolicy, String txnId, Object[] args) {
        String hmac = "";
        String message = "";
        K0 = getK0();
        try {
            switch (hmacPolicy) {

                case GENERAL:
                    if (!TextUtils.isEmpty(K0)) {
                        message = SDKUtils.getAppId() + "|"
                                + SDKUtils.getDeviceId() + "|"
                                + UpiSDKContext.getInstance().getUserMobile() + "|"
                                + txnId;
                        Log.d("Message of Hash", message);
                        Log.d("Message of txnId", txnId);
                        byte[] calculatedHash = cryptLib.SHA256(message);
                        byte[] encryptedData = cryptLib.encrypt(calculatedHash,
                                cryptLib.hexStringToByteArray(K0));
                        hmac = Base64.encodeToString(encryptedData, Base64.NO_WRAP);
                        Log.d("Existing K0", K0);
                        Log.d("HMAC", hmac);
                    }
                    break;

                case APP_REGISTRATION:
                    PublicKey issuerPublicKey = null;
                   // Log.d("public key frm fetchConfigParams",SDKUtils.getPublicKey());
                    issuerPublicKey = CryptLib.readPublicKeyFromString(SDKUtils.getPublicKey());

                    if (issuerPublicKey == null) {
                        return null;
                    }
                    generateK0();
                    Log.d("Generated K0", K0);
                    message = K0 + "|"
                            + SDKUtils.getDeviceId() + "|"
                            + UpiSDKContext.getInstance().getUserMobile() + "|"
                            + SDKUtils.getAppId();
                    hmac = CryptLib.encryptDataUsinRSA(message, issuerPublicKey);
                 //   Log.d("register app hmac public key",issuerPublicKey.toString());
                    //Log.d("generated hmac",hmac);
                    break;

                case PAYMENT:
                    if (args == null
                            || args.length <= 0
                            || !(args[0] instanceof PaymentRequest)
                            ) {
                        Log.d("HMac Generation", "HMac is not generated as Payment Request object is missing");
                        return null;
                    }

                    PaymentRequest payReq = (PaymentRequest) args[0];
                    String payerAcVpa = "";
                    String payeeAcVpa = "";
                    String txnAmtStr = "";
                    if (payReq.getPayer() != null) {
                        payerAcVpa = payReq.getPayer().getPayerAcVpa();
                    }

                    if (payReq.getPayees() != null
                            && !payReq.getPayees().isEmpty()
                            && payReq.getPayees().get(0) != null) {
                        payeeAcVpa = payReq.getPayees().get(0).getPayeeAcVpa();
                    }

                    if (payReq.getPayer().getPayerAmt() != null) {
                        DecimalFormat df = new DecimalFormat("#.00");
                        txnAmtStr = df.format(payReq.getPayer().getPayerAmt());
                    }

                    if (!TextUtils.isEmpty(K0)) {
                        message = SDKUtils.getAppId() + "|"
                                + SDKUtils.getDeviceId() + "|"
                                + UpiSDKContext.getInstance().getUserMobile() + "|"
                                + txnId + "|"
                                + payerAcVpa + "|"
                                + payeeAcVpa + "|"
                                + txnAmtStr;
                        Log.d("Message of Hash", message);
                        byte[] calculatedHash = cryptLib.SHA256(message);
                        byte[] encryptedData = cryptLib.encrypt(calculatedHash,
                                cryptLib.hexStringToByteArray(K0));
                        hmac = Base64.encodeToString(encryptedData, Base64.NO_WRAP);
                        Log.d("Existing K0", K0);
                        Log.d("HMAC", hmac);
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hmac;
    }

    private String generateK0() {
        try {
            K0 = cryptLib.generateRandomAes256Key();
            UpiSDKContext.prefs.setK0(K0);

          //  inserDB(UpiSDKContext.getInstance().getUserId().toString(),K0);
            Log.d(" ", UpiSDKContext.prefs.getK0());
            return K0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    private void inserDB(String userid, String k0) {
//
//        dbHelper=new DatabaseHelper(UpiSDKContext.getInstance().getApplicationContext());
//        dbHelper.insertValueInDb(userid,k0);
//
//    }

    public String getK0() {
        Log.d("getK0",UpiSDKContext.prefs != null ? UpiSDKContext.prefs.getK0() : "");

        return UpiSDKContext.prefs != null ? UpiSDKContext.prefs.getK0() : "";
    }

}
