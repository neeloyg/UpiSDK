package com.upi.sdk.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.rssoftware.upiint.schema.ChannelType;
import com.rssoftware.upiint.schema.DeviceDetails;
import com.rssoftware.upiint.schema.DeviceFingerprint;
import com.rssoftware.upiint.schema.PaymentRequest;
import com.upi.sdk.CryptLib;
import com.upi.sdk.ILocationCallback;
import com.upi.sdk.core.UPPSDKConstants;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.BankAccountBean;
import com.upi.sdk.services.GPSTracker;
import com.upi.sdk.services.HmacGenerationPolicy;
import com.upi.sdk.services.HmacGenerator;

//import org.apache.http.conn.util.InetAddressUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

/**
 * Created by NeeloyG on 26-04-2016.
 */
public class SDKUtils {

    private static String DEVICE_ID = "";
    public static String TOKEN = "";
    //private static String K0 = "";
    public static String XML_PAYLOAD_STRING = "";
    public static String PUBLIC_KEY = "";
    private static double latitude = 0;
    private static double longitude = 0;
    private static CryptLib cryptLib = new CryptLib();
    private static HmacGenerator hmacGenerator = new HmacGenerator();
    public static String BANK_NAME = "";
    public static ArrayList<BankAccountBean> LIST_ACCOUNT_MASKED = new ArrayList<BankAccountBean>();
    public static String VMN_NUMBER = "";
    public static String VMN_MESSAGE = "";
    public static String SDK_VERSION = "1.8";
    public static String GCM_ID="";
    public static String K0HEX="";
    public static String REF_URL="";
    public static String PSP="";

    public static String getDeviceId() {
        if (TextUtils.isEmpty(DEVICE_ID)) {
            DEVICE_ID = getDeviceId(UpiSDKContext.getInstance().getApplicationContext());
        }
        return DEVICE_ID;

    }

    public static String getMobileNumber() {
        return UpiSDKContext.getInstance().getUserMobile();

    }

    public static String getAppId() {
        return UpiSDKContext.getInstance().getApplicationContext().getPackageName();

    }

    public static String getToken() {
        return TOKEN;

    }


    public static String getPublicKey() {
        return PUBLIC_KEY;

    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static DeviceDetails getDeviceDetails(HmacGenerationPolicy hmacPolicy, Object... args) {
        List<Address> addresses = new ArrayList<Address>();
        GPSTracker gps = new GPSTracker(UpiSDKContext.getInstance().getApplicationContext(), new ILocationCallback() {
            @Override
            public void getUpdatedLocation(double ilatitude, double ilongitude) {
                latitude = ilatitude;
                longitude = ilongitude;
            }
        });
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            try {
                Geocoder gcd = new Geocoder(UpiSDKContext.getInstance().getApplicationContext(), Locale.getDefault());
                addresses = gcd.getFromLocation(latitude, longitude, 1);

            } catch (Exception ex) {

            }
        } /*else {
            gps.showSettingsAlert();

        }*/
        DeviceDetails deviceDetails = new DeviceDetails();
        deviceDetails.setMobile(UpiSDKContext.getInstance().getUserMobile());
        deviceDetails.setTxnId(UUID.randomUUID().toString());
        deviceDetails.setDeviceRegId(getDeviceId(UpiSDKContext.getInstance().getApplicationContext()));
        deviceDetails.setUserId(UpiSDKContext.getInstance().getUserId());
        deviceDetails.setAppId(UpiSDKContext.getInstance().getApplicationContext().getPackageName());
        deviceDetails.setChannel(UPPSDKConstants.CURRENT_CHANNEL_ID);
        deviceDetails.setDeviceType(ChannelType.MOB.name());
        deviceDetails.setDeviceOS("Android " + "" + Build.VERSION.RELEASE);
        deviceDetails.setGeoCode("" + latitude + "," + longitude);
        if (addresses != null && addresses.size() > 0)
            deviceDetails.setLocation(addresses.get(0).getAddressLine(0) + addresses.get(0).getAddressLine(1) + (addresses.get(0).getPostalCode() == null ? "" : ", " + addresses.get(0).getPostalCode()));
        else
            deviceDetails.setLocation("");
        deviceDetails.setIp(getLocalIpAddress());
        deviceDetails.setApplcation(UpiSDKContext.getInstance().getApplicationContext().getPackageName());
        deviceDetails.setCapabilty("100");
        //deviceDetails.setHmac(generateHMAC(hmacPolicy, deviceDetails.getTxnId(), args));
        deviceDetails.setHmac(hmacGenerator.generateHMAC(hmacPolicy, deviceDetails.getTxnId(), args));
        deviceDetails.setDevicePushToken(GCM_ID);
        return deviceDetails;
    }

    public static DeviceDetails getDeviceDetailsForgetPasswordFlow(Context context, HmacGenerationPolicy hmacPolicy,
                                                                   String userId, String mobileNumber) {
        List<Address> addresses = new ArrayList<Address>();
        GPSTracker gps = new GPSTracker(context, new ILocationCallback() {
            @Override
            public void getUpdatedLocation(double ilatitude, double ilongitude) {
                latitude = ilatitude;
                longitude = ilongitude;
            }
        });
        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            try {
                Geocoder gcd = new Geocoder(context, Locale.getDefault());
                addresses = gcd.getFromLocation(latitude, longitude, 1);

            } catch (Exception ex) {

            }
        } /*else {
            gps.showSettingsAlert();

        }*/
        DeviceDetails deviceDetails = new DeviceDetails();
        deviceDetails.setMobile(mobileNumber);
        deviceDetails.setTxnId(UUID.randomUUID().toString());
        deviceDetails.setDeviceRegId(getDeviceId(context));
        deviceDetails.setUserId(userId);
        deviceDetails.setAppId(context.getPackageName());
        deviceDetails.setChannel(UPPSDKConstants.CURRENT_CHANNEL_ID);
        deviceDetails.setDeviceType(ChannelType.MOB.name());
        deviceDetails.setDeviceOS("Android " + "" + Build.VERSION.RELEASE);
        deviceDetails.setGeoCode("" + latitude + "," + longitude);
        if (addresses != null && addresses.size() > 0)
            deviceDetails.setLocation(addresses.get(0).getAddressLine(0) + addresses.get(0).getAddressLine(1) + (addresses.get(0).getPostalCode() == null ? "" : ", " + addresses.get(0).getPostalCode()));
        else
            deviceDetails.setLocation("");
        deviceDetails.setIp(getLocalIpAddress());
        deviceDetails.setApplcation(context.getPackageName());
        deviceDetails.setCapabilty("100");
        deviceDetails.setHmac(hmacGenerator.generateHMAC(hmacPolicy, deviceDetails.getTxnId(), new Object[]{deviceDetails.getAppId(),
                deviceDetails.getDeviceRegId(), deviceDetails.getMobile()}));
        deviceDetails.setDevicePushToken(GCM_ID);
        return deviceDetails;
    }

    public static DeviceFingerprint getDeviceFingerprint() {
        DeviceFingerprint deviceFingerprint = new DeviceFingerprint();
        deviceFingerprint.setDeviceRegId(getDeviceId());
        deviceFingerprint.setMobile(UpiSDKContext.getInstance().getUserMobile());

        return deviceFingerprint;
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @return address or empty string
     */
    public static String getLocalIpAddress() {
        boolean useIPv4 = true;
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

/*    public static String generateHMAC(HmacGenerationPolicy hmacPolicy, String txnId, Object[] args){
        String hmac="";
        String message="";
        try {
            switch (hmacPolicy) {

                case GENERAL:
                    if (!TextUtils.isEmpty(K0)) {
                        message = SDKUtils.getAppId() + "|"
                                + SDKUtils.getDeviceId() + "|"
                                + UpiSDKContext.getInstance().getUserMobile() + "|"
                                + txnId;
                        Log.d("Message of Hash", message);
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

                    issuerPublicKey = CryptLib.readPublicKeyFromString(SDKUtils.getPublicKey());

                    if (issuerPublicKey == null) {
                        return null;
                    }
                    generateK0();
                    Log.d("Generated K0", K0);
                    message=K0 +"|"
                            + SDKUtils.getDeviceId()+"|"
                            + UpiSDKContext.getInstance().getUserMobile()+"|"
                            + SDKUtils.getAppId();
                    hmac=CryptLib.encryptDataUsinRSA(message, issuerPublicKey);
                    break;

                case PAYMENT:
                    if (args == null
                            || args.length <= 0
                            || !(args[0] instanceof PaymentRequest)
                            ) {
                        Log.d("HMac Generation", "HMac is not generated as Payment Request object is missing");
                        return null;
                    }

                    PaymentRequest payReq = (PaymentRequest)args[0];
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

    public static String generateK0() {
        try {
            K0 = cryptLib.generateRandomAes256Key();
            return K0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }*/

    /**
     * @param context
     * @return {@link Boolean}
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean outcome = false;

        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo[] networkInfos = cm.getAllNetworkInfo();

            for (NetworkInfo tempNetworkInfo : networkInfos) {

                /**
                 * Can also check if the user is in roaming
                 */
                if (tempNetworkInfo != null && tempNetworkInfo.isConnected()) {
                    outcome = true;
                    break;
                }
            }
        }

        return outcome;
    }

    public static String getUsedK0() {
        return hmacGenerator.getK0();
    }

    public static String getMaskedAccount(String accNumber) {
        String mod_acc = "";
        int str_length = accNumber.length();
        mod_acc = accNumber.substring(str_length - 6);
        String final_acc = "";
        for (int i = 0; i < str_length-6; i++) {
            if (i == 0)
                final_acc = "X";
            else
                final_acc = final_acc + "X";
        }
        return final_acc + mod_acc;
    }

    public static String getSDKVersion() {
        return SDK_VERSION;
    }

    /**
     * Checks if the device is rooted.
     *
     * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
     */
    public static boolean isRooted() {

        // get from build info
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        // check if /system/app/Superuser.apk is present
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e1) {
            // ignore
        }

        // try executing commands
        return canExecuteCommand("/system/xbin/which su")
                || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }

    // executes a command on the system
    private static boolean canExecuteCommand(String command) {
        boolean executedSuccesfully;
        try {
            Runtime.getRuntime().exec(command);
            executedSuccesfully = true;
        } catch (Exception e) {
            executedSuccesfully = false;
        }

        return executedSuccesfully;
    }

    public static boolean isSimSupport(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);

    }

    public static String getEncryptedPassword(String password) {
        String encrypted_text = "";
        try {
            CryptLib cryptLib = new CryptLib();
            byte[] passwordBytes = cryptLib.SHA256(password);
            String hexString = bytesToHex(passwordBytes);
            Log.e("str", hexString);
            Log.e("public_key",PUBLIC_KEY);
            encrypted_text = cryptLib.encryptDataUsinRSA(hexString, cryptLib.readPublicKeyFromString(PUBLIC_KEY));

        } catch (Exception e) {
            e.printStackTrace();
            encrypted_text = "";
        }
        return encrypted_text;
    }

    private static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static String getEncryptedOTP(String otp) {
        String encrypted_text = "";
        try {
            CryptLib cryptLib = new CryptLib();
            encrypted_text = cryptLib.encryptDataUsinRSA(otp, cryptLib.readPublicKeyFromString(PUBLIC_KEY));

        } catch (Exception e) {
            e.printStackTrace();
            encrypted_text = "";
        }
        return encrypted_text;
    }



}
