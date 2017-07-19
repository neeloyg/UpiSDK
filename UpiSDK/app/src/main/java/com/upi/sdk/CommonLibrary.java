package com.upi.sdk;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.rssoftware.upiint.schema.*;
import com.rssoftware.upiint.schema.Error;
import com.upi.sdk.components.ListKeys;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.CLInitializationMode;
import com.upi.sdk.domain.UserProfile;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessFlow;
import com.upi.sdk.processflow.initialization.RequestOtpSubflow;
import com.upi.sdk.processflow.transaction.TransactionIdGeneratorSubflow;
import com.upi.sdk.services.HmacGenerationPolicy;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;
import com.upi.sdk.utils.CLConstants;
import com.upi.sdk.utils.SDKUtils;
import com.upi.sdk.utils.TrustCreator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.npci.upi.security.services.CLRemoteResultReceiver;
import org.npci.upi.security.services.CLServices;
import org.npci.upi.security.services.ServiceConnectionStatusNotifier;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NeeloyG on 26-04-2016.
 */
public final class CommonLibrary {
    private CLServices clServices;
    private String token = null;
    private boolean clInitialized = false;
    private CryptLib cryptLib = new CryptLib();
    private static final String keyCode = "NPCI";
    private static final String TOKEN_INIT_MODE = "initial";
    private static final String TOKEN_ROTATE_MODE = "rotate";
    private String credAllowedString = "";
    private int get_token_hit_count = 0;

    public static void initializeCL(final Context applicationContext, final UserProfile user, final CLInitializationMode mode,
                                    final ServiceCallback<CommonLibrary> callback) {
        /**
         * Initiate Common Library Services before using. If the service is successfully bound, serviceConnected
         * method of the ServiceConnectionStatusNotifier will be called.
         */

        final CommonLibrary commonLibrary = new CommonLibrary();
        Log.d("CL Init", "Init Cl called");
        Log.d("CL Init", "ApplicationContext:" + applicationContext);
        Log.d("CL Init", "User:" + user);
        Log.d("CL Init", "Mode:" + mode);
        Log.d("CL Init", "callback:" + callback);
        CLServices.initService(applicationContext, new ServiceConnectionStatusNotifier() {
            @Override
            public void serviceConnected(CLServices services) {
                try {
                    Log.d("CL Service", "Service connected called");
                    commonLibrary.clServices = services;

                    Log.d("CL Service", "Service connected");
                    switch (mode) {
                        case INIT_TOKEN:
                            commonLibrary.integrateLibrary(TOKEN_INIT_MODE, applicationContext, callback);
                            break;

                        case ROTATE_TOKEN:
                            commonLibrary.integrateLibrary(TOKEN_ROTATE_MODE, applicationContext, callback);
                            break;

                        default:
                            if (user.getClToken() != null && user.getClToken().length() > 0) {
                                Log.d("CL Service", "Service connected called - within if");
                                byte[] tokenBytes = Base64.decode(user.getClToken(), Base64.NO_WRAP);
                                String token_hex = "";
                                token_hex = commonLibrary.cryptLib.byteArrayToHex(tokenBytes);
                                commonLibrary.token = token_hex;
                            }
                            callback.onSuccess(commonLibrary);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(WebServiceStatus.FAILURE, null);
                }

            }

            @Override
            public void serviceDisconnected() {
                Log.d("CL Service", "Service disconnected");
                commonLibrary.clServices = null;
            }
        });
    }

    public CLServices getClServices() {
        if (clServices == null)
            return null;
        else
            return clServices;
    }

    /**
     * Return credentials for all type of requests
     */
    public void getCredential(final PaymentRequest request, final ServiceCallback callback) {

        if (request.getCredAllowed() != null) {
            credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                    "{\n" +
                    "\t\t\"type\": \"" + request.getCredAllowed().getType() + "\",\n" +
                    "\t\t\"subtype\": \"" + request.getCredAllowed().getSubtype() + "\",\n" +
                    "\t\t\"dType\": \"" + request.getCredAllowed().getDtype() + "\",\n" +
                    "\t\t\"dLength\": " + request.getCredAllowed().getDlemgth() + "\n" +
                    "\t}]\n" + "}";

            request.setCredAllowed(null);

        } else {
            credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                    "{\n" +
                    "\t\t\"type\": \"PIN\",\n" +
                    "\t\t\"subtype\": \"MPIN\",\n" +
                    "\t\t\"dType\": \"NUM\",\n" +
                    "\t\t\"dLength\": 6\n" +
                    "\t}]\n" + "}";
        }


        // Create Configuration
        final JSONObject configuration = new JSONObject();
        ;
        try {
            //configuration = new JSONObject();
            //TODO Have to add bank name...
            if (request.getBank_name() != null && request.getBank_name().length() > 0)
                configuration.put("payerBankName", request.getBank_name());
            else
                configuration.put("payerBankName", SDKUtils.BANK_NAME);

            configuration.put("backgroundColor", "#FFFFFF");
            Log.i("configuration", configuration.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create Salt
        DecimalFormat df = new DecimalFormat("#.00");
        String txnAmountStr = df.format(request.getTxnTotalamt());
        final JSONObject salt = new JSONObject();
        try {
            //salt = new JSONObject();
            salt.put("txnId", request.getTxnId());
            salt.put("txnAmount", txnAmountStr);
            salt.put("deviceId", SDKUtils.getDeviceId());
            salt.put("appId", SDKUtils.getAppId());
            salt.put("mobileNumber", SDKUtils.getMobileNumber());
            salt.put("payerAddr", request.getPayer().getPayerAcVpa());
            salt.put("payeeAddr", request.getPayees().get(0).getPayeeAcVpa());
            Log.i("salt", salt.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String trustStr = null;
        try {
            StringBuilder trustParamBuilder = new StringBuilder(100);
            trustParamBuilder.append(txnAmountStr).append(CLConstants.SALT_DELIMETER)
                    .append(request.getTxnId()).append(CLConstants.SALT_DELIMETER)
                    .append(request.getPayer().getPayerAcVpa()).append(CLConstants.SALT_DELIMETER)
                    .append(request.getPayees().get(0).getPayeeAcVpa()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getAppId()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getMobileNumber()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getDeviceId());
            trustStr = TrustCreator.createTrust(trustParamBuilder.toString(), this.token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String trust = trustStr;

        // Create Pay Info
        final JSONArray payInfoArray = new JSONArray();
        try {
            JSONObject jsonPayeeName = new JSONObject();
            jsonPayeeName.put("name", "payeeName");
            jsonPayeeName.put("value", request.getPayees().get(0).getName());
            payInfoArray.put(jsonPayeeName);

            JSONObject txnAmount = new JSONObject();
            txnAmount.put("name", "txnAmount");
            txnAmount.put("value", txnAmountStr);
            payInfoArray.put(txnAmount);

            JSONObject jsonNote = new JSONObject();
            jsonNote.put("name", "note");
            jsonNote.put("value", request.getNote());
            payInfoArray.put(jsonNote);

            JSONObject jsonRefId = new JSONObject();
            jsonRefId.put("name", "refId");
            jsonRefId.put("value", request.getTxnId());
            payInfoArray.put(jsonRefId);

            JSONObject jsonRefUrl = new JSONObject();
            jsonRefUrl.put("name", "refUrl");
            jsonRefUrl.put("value", SDKUtils.REF_URL);
            payInfoArray.put(jsonRefUrl);

            JSONObject jsonAccount = new JSONObject();
            jsonAccount.put("name", "account");
            jsonAccount.put("value", request.getPayer().getBankAcNumber());
            payInfoArray.put(jsonAccount);

            Log.i("payInfo", payInfoArray.toString());
            //intent.putExtra("payInfo", payInfoArray.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /**
         * create a CLRemoteResultReceiver instance to receive the encrypted credential.
         */
        final CLRemoteResultReceiver remoteResultReceiver = new CLRemoteResultReceiver(new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                if (resultData != null) {
                    if (resultData.getString("error") != null && resultData.getString("error").equalsIgnoreCase("USER_ABORTED")) {
                        request.getiStateListener().getState(State.IDLE);
                    } else {
                        parseResult(resultData, callback);
                    }

                } else {
                    request.getiStateListener().getState(State.IDLE);
                }
            }
        });

        if (SDKUtils.isNetworkAvailable(UpiSDKContext.getInstance().getApplicationContext())) {
            ListKeys.populateXmlPayLoad(new ServiceCallback<String>(String.class) {
                @Override
                public void onSuccess(String xmlPayLoad) {
                    clServices.getCredential(keyCode, xmlPayLoad, credAllowedString,
                            configuration.toString(), salt.toString(), payInfoArray.toString(), trust, CLConstants.CL_LANGUAGE.name(), remoteResultReceiver);
                }

                @Override
                public void onError(WebServiceStatus status, List<Error> errors) {
                    callback.onError(status, errors);
                }
            });
        } else {
            Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Return credentials for all type of requests
     */
    public void getCredential(final PaymentAuthRequest request, final ServiceCallback callback) {

        //getPayloadString();

        if (request.getCredAllowed() != null) {
            credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                    "{\n" +
                    "\t\t\"type\": \"" + request.getCredAllowed().getType() + "\",\n" +
                    "\t\t\"subtype\": \"" + request.getCredAllowed().getSubtype() + "\",\n" +
                    "\t\t\"dType\": \"" + request.getCredAllowed().getDtype() + "\",\n" +
                    "\t\t\"dLength\": " + request.getCredAllowed().getDlemgth() + "\n" +
                    "\t}]\n" + "}";

            Log.e("credAllowedString", credAllowedString);

            request.setCredAllowed(null);

        } else {
            credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                    "{\n" +
                    "\t\t\"type\": \"PIN\",\n" +
                    "\t\t\"subtype\": \"MPIN\",\n" +
                    "\t\t\"dType\": \"NUM\",\n" +
                    "\t\t\"dLength\": 6\n" +
                    "\t}]\n" + "}";
            Log.e("credDefaultString", credAllowedString);
        }


        // Create Configuration
        final JSONObject configuration = new JSONObject();
        ;
        try {
            //TODO Have to add bank name...
            configuration.put("payerBankName", SDKUtils.BANK_NAME);
            configuration.put("backgroundColor", "#FFFFFF");
            Log.i("configuration", configuration.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Create Salt
        final JSONObject salt = new JSONObject();
        DecimalFormat df = new DecimalFormat("#.00");
        String txnAmountStr = df.format(request.getPayerAmt());
        try {
            salt.put("txnId", request.getTxnId());
            salt.put("txnAmount", txnAmountStr);
            salt.put("deviceId", SDKUtils.getDeviceId());
            salt.put("appId", SDKUtils.getAppId());
            salt.put("mobileNumber", SDKUtils.getMobileNumber());
            salt.put("payerAddr", request.getPayerAcVpa());
            salt.put("payeeAddr", request.getPayeeAcVpa());
            Log.i("salt", salt.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String trustStr = null;
        try {
            StringBuilder trustParamBuilder = new StringBuilder(100);
            trustParamBuilder.append(txnAmountStr).append(CLConstants.SALT_DELIMETER)
                    .append(request.getTxnId()).append(CLConstants.SALT_DELIMETER)
                    .append(request.getPayerAcVpa()).append(CLConstants.SALT_DELIMETER)
                    .append(request.getPayeeAcVpa()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getAppId()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getMobileNumber()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getDeviceId());
            trustStr = TrustCreator.createTrust(trustParamBuilder.toString(), this.token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String trust = trustStr;
        // Create Pay Info
        final JSONArray payInfoArray = new JSONArray();
        try {
            JSONObject jsonPayeeName = new JSONObject();
            jsonPayeeName.put("name", "payeeName");
            jsonPayeeName.put("value", request.getPayeeName());
            payInfoArray.put(jsonPayeeName);

            JSONObject txnAmount = new JSONObject();
            txnAmount.put("name", "txnAmount");
            txnAmount.put("value", request.getPayerAmt());
            payInfoArray.put(txnAmount);

            //JSONObject jsonNote = new JSONObject();
            //jsonNote.put("name", "note");
            //TODO Have to add note...
            //jsonNote.put("value", "test");
            //payInfoArray.put(jsonNote);

            JSONObject jsonRefId = new JSONObject();
            jsonRefId.put("name", "refId");
            jsonRefId.put("value", request.getTxnId());
            payInfoArray.put(jsonRefId);

            JSONObject jsonRefUrl = new JSONObject();
            jsonRefUrl.put("name", "refUrl");
            jsonRefUrl.put("value", SDKUtils.REF_URL);
            payInfoArray.put(jsonRefUrl);

            //JSONObject jsonAccount = new JSONObject();
            //jsonAccount.put("name", "account");
            //TODO Have to add account number...
            //jsonAccount.put("value", "122XXX423");
            //payInfoArray.put(jsonAccount);

            Log.i("payInfo", payInfoArray.toString());
            //intent.putExtra("payInfo", payInfoArray.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /**
         * create a CLRemoteResultReceiver instance to receive the encrypted credential.
         */
        final CLRemoteResultReceiver remoteResultReceiver = new CLRemoteResultReceiver(new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                if (resultData != null) {
                    if (resultData.getString("error") != null && resultData.getString("error").equalsIgnoreCase("USER_ABORTED")) {
                        request.getiStateListener().getState(State.IDLE);
                    } else {
                        parseResult(resultData, callback);
                    }
                } else {
                    request.getiStateListener().getState(State.IDLE);
                }
            }
        });

        ListKeys.populateXmlPayLoad(new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String xmlPayload) {
                clServices.getCredential(keyCode, xmlPayload, credAllowedString,
                        configuration.toString(), salt.toString(), payInfoArray.toString(), trust, CLConstants.CL_LANGUAGE.name(), remoteResultReceiver);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                callback.onError(status, errors);
            }
        });

        /**
         * Call get Credential
         */
        /*if(SDKUtils.getXmlPayloadString()!=null && SDKUtils.getXmlPayloadString().length()>0)
        clServices.getCredential(keyCode, SDKUtils.getXmlPayloadString(), credAllowedString,
                configuration.toString(), salt.toString(), payInfoArray.toString(), trustStr, CLConstants.CL_LANGUAGE, remoteResultReceiver);
        else
            getPayloadString();
*/
    }

    /**
     * Return credentials for all type of requests
     */
    public void getCredentialBalanceEnquiry(final BalanceEnquiry balanceEnquiryReq,
                                            final ServiceCallback callback) {

        if (balanceEnquiryReq.getCredAllowed() != null) {
            if (balanceEnquiryReq.getCredAllowed().getDtype() != null
                    && balanceEnquiryReq.getCredAllowed().getDlemgth() != null) {
                String D_TYPE = "";
                if (balanceEnquiryReq.getCredAllowed().getDtype().equalsIgnoreCase("Numeric")) {
                    D_TYPE = "NUM";
                } else {
                    D_TYPE = "ALPH | NUM";
                }
                credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                        "{\n" +
                        "\t\t\"type\": \"PIN\",\n" +
                        "\t\t\"subtype\": \"MPIN\",\n" +
                        "\t\t\"dType\": \"" + D_TYPE + "\",\n" +
                        "\t\t\"dLength\": " + balanceEnquiryReq.getCredAllowed().getDlemgth() + "\n" +
                        "\t}]\n" + "}";
                Log.e("credAllowedString", credAllowedString);
            }else{
                credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                        "{\n" +
                        "\t\t\"type\": \"PIN\",\n" +
                        "\t\t\"subtype\": \"MPIN\",\n" +
                        "\t\t\"dType\": \"NUM\",\n" +
                        "\t\t\"dLength\": 6\n" +
                        "\t}]\n" + "}";
                Log.e("credDefaultString", credAllowedString);
            }
        } else {
            credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                    "{\n" +
                    "\t\t\"type\": \"PIN\",\n" +
                    "\t\t\"subtype\": \"MPIN\",\n" +
                    "\t\t\"dType\": \"NUM\",\n" +
                    "\t\t\"dLength\": 6\n" +
                    "\t}]\n" + "}";
            Log.e("credDefaultString", credAllowedString);
        }


        // Create Configuration
        final JSONObject configuration = new JSONObject();
        ;
        try {
            //TODO Have to add bank name...
            if (balanceEnquiryReq.getBank_name() != null && balanceEnquiryReq.getBank_name().length() > 0)
                configuration.put("payerBankName", balanceEnquiryReq.getBank_name());
            else
                configuration.put("payerBankName", SDKUtils.BANK_NAME);
            configuration.put("backgroundColor", "#FFFFFF");
            Log.i("configuration", configuration.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Create Salt
        final JSONObject salt = new JSONObject();
        try {
            salt.put("txnId", balanceEnquiryReq.getTxnId());
            salt.put("deviceId", SDKUtils.getDeviceId());
            salt.put("appId", SDKUtils.getAppId());
            salt.put("mobileNumber", SDKUtils.getMobileNumber());
            salt.put("payerAddr", balanceEnquiryReq.getVpa());
            Log.i("salt", salt.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String trustStr = null;
        try {
            StringBuilder trustParamBuilder = new StringBuilder(100);
            trustParamBuilder.append(balanceEnquiryReq.getTxnId()).append(CLConstants.SALT_DELIMETER)
                    .append(balanceEnquiryReq.getVpa()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getAppId()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getMobileNumber()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getDeviceId());
            trustStr = TrustCreator.createTrust(trustParamBuilder.toString(), this.token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String newtrust = trustStr;
        // Create Pay Info
        final JSONArray payInfoArray = new JSONArray();
        try {
            JSONObject jsonAccount = new JSONObject();
            jsonAccount.put("name", "account");
            jsonAccount.put("value", balanceEnquiryReq.getBankAcNumber());
            payInfoArray.put(jsonAccount);

            Log.i("payInfo", payInfoArray.toString());
            //intent.putExtra("payInfo", payInfoArray.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /**
         * create a CLRemoteResultReceiver instance to receive the encrypted credential.
         */
        final CLRemoteResultReceiver remoteResultReceiver = new CLRemoteResultReceiver(new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                Log.e("result_code", "" + resultCode);
                if (resultData != null) {
                    if (resultData.getString("error") != null && resultData.getString("error").equalsIgnoreCase("USER_ABORTED")) {
                        balanceEnquiryReq.getiStateListener().getState(State.IDLE);
                    } else {
                        parseResult(resultData, callback);
                    }

                } else {
                    balanceEnquiryReq.getiStateListener().getState(State.IDLE);
                }

            }
        });

        ListKeys.populateXmlPayLoad(new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String xmlPayload) {
                clServices.getCredential(keyCode, xmlPayload, credAllowedString,
                        configuration.toString(), salt.toString(), payInfoArray.toString(), newtrust, CLConstants.CL_LANGUAGE.name(), remoteResultReceiver);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                callback.onError(status, errors);
            }
        });

        /**
         * Call get Credential
         */
        /*if(SDKUtils.getXmlPayloadString()!=null && SDKUtils.getXmlPayloadString().length()>0)
        clServices.getCredential(keyCode, SDKUtils.getXmlPayloadString(), credAllowedString,
                configuration.toString(), salt.toString(), payInfoArray.toString(), trustStr, CLConstants.CL_LANGUAGE, remoteResultReceiver);
        else
            getPayloadString();
*/
    }

    /**
     * Return credentials for all type of requests
     */
    public void getCredentialRegMob(final ReqRegMobile reqRegMobile,
                                    final ServiceCallback callback) {

        //getPayloadString();

//        {
//            "type": "PIN",
//                "subtype": "ATMPIN",
//                "dType": "NUM",
//                "dLength": "4"
//        },

        if (reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() != null)
            Log.e("getAccetptedCredSubType", reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType());
        if (reqRegMobile.getLinkAccountDetails().getCredDataType() != null)
            Log.e("getCredDataType", reqRegMobile.getLinkAccountDetails().getCredDataType());
        if (reqRegMobile.getLinkAccountDetails().getCredLength() != null)
            Log.e("getCredLength", "" + reqRegMobile.getLinkAccountDetails().getCredLength());
        if (reqRegMobile.getOtpCredDataType()!= null)
            Log.e("getOtpCredDataType", "" + reqRegMobile.getOtpCredDataType());
        if (Integer.toString(reqRegMobile.getOtpCredLength())!= null)
            Log.e("getOtpCredLength", "" + Integer.toString(reqRegMobile.getOtpCredLength()));
        String OTP_TYPE="NUM";
        int OTP_LENGTH=6;

        if (reqRegMobile.getOtpCredDataType()!=null &&
                reqRegMobile.getOtpCredDataType().equalsIgnoreCase("Numeric")) {
            OTP_TYPE = "NUM";
        } else {
            OTP_TYPE = "ALPH | NUM";
        }

        if(Integer.toString(reqRegMobile.getOtpCredLength())!=null){
            OTP_LENGTH=reqRegMobile.getOtpCredLength();
        }

        if (reqRegMobile.getMobRegFormat() != null) {

            if (reqRegMobile.getMobRegFormat().equalsIgnoreCase("FORMAT2")) {
                if (reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() != null
                        && reqRegMobile.getLinkAccountDetails().getCredDataType() != null
                        && reqRegMobile.getLinkAccountDetails().getCredLength() != null) {
                    String D_TYPE = "";

                    if (reqRegMobile.getLinkAccountDetails().getCredDataType().equalsIgnoreCase("Numeric")) {
                        D_TYPE = "NUM";
                    } else {
                        D_TYPE = "ALPH | NUM";
                    }





                    if (reqRegMobile.getLinkAccountDetails().getAtmCredDataType() != null
                            && reqRegMobile.getLinkAccountDetails().getAtmCredLength() != null) {

                        String ATM_D_TYPE = "";
                        if (reqRegMobile.getLinkAccountDetails().getAtmCredDataType().equalsIgnoreCase("Numeric")) {
                            ATM_D_TYPE = "NUM";
                        } else {
                            ATM_D_TYPE = "ALPH | NUM";
                        }

                        credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                                "{\n" +
                                "\t\t\"type\": \"OTP\",\n" +
                                "\t\t\"subtype\": \"SMS\",\n" +
                                "\t\t\"dType\": \"" +OTP_TYPE+ "\",\n" +
                                "\t\t\"dLength\": " +OTP_LENGTH+ "\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"" + reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() + "\",\n" +
                                "\t\t\"dType\": \"" + D_TYPE + "\",\n" +
                                "\t\t\"dLength\": " + reqRegMobile.getLinkAccountDetails().getCredLength() + "\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"ATMPIN\",\n" +
                                "\t\t\"dType\": \"" + ATM_D_TYPE + "\",\n" +
                                "\t\t\"dLength\": " + reqRegMobile.getLinkAccountDetails().getAtmCredLength() + "\n" +
                                "\t}]\n" + "}";
                        Log.e("first", credAllowedString);

                    } else {
                        credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                                "{\n" +
                                "\t\t\"type\": \"OTP\",\n" +
                                "\t\t\"subtype\": \"SMS\",\n" +
                                "\t\t\"dType\": \""+OTP_TYPE+"\",\n" +
                                "\t\t\"dLength\": "+OTP_LENGTH+"\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"" + reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() + "\",\n" +
                                "\t\t\"dType\": \"" + D_TYPE + "\",\n" +
                                "\t\t\"dLength\": " + reqRegMobile.getLinkAccountDetails().getCredLength() + "\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"ATMPIN\",\n" +
                                "\t\t\"dType\": \"NUM\",\n" +
                                "\t\t\"dLength\": 4\n" +
                                "\t}]\n" + "}";
                    }
                    Log.e("second", credAllowedString);

                } else {
                    if (reqRegMobile.getLinkAccountDetails().getAtmCredDataType() != null
                            && reqRegMobile.getLinkAccountDetails().getAtmCredLength() != null) {

                        String ATM_D_TYPE = "";
                        if (reqRegMobile.getLinkAccountDetails().getAtmCredDataType().equalsIgnoreCase("Numeric")) {
                            ATM_D_TYPE = "NUM";
                        } else {
                            ATM_D_TYPE = "ALPH | NUM";
                        }

                        credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                                "{\n" +
                                "\t\t\"type\": \"OTP\",\n" +
                                "\t\t\"subtype\": \"SMS\",\n" +
                                "\t\t\"dType\": \""+OTP_TYPE+"\",\n" +
                                "\t\t\"dLength\": "+OTP_LENGTH+"\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"MPIN\",\n" +
                                "\t\t\"dType\":\"ALPH | NUM\",\n" +
                                "\t\t\"dLength\": 6\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"ATMPIN\",\n" +
                                "\t\t\"dType\": \"" + ATM_D_TYPE + "\",\n" +
                                "\t\t\"dLength\": " + reqRegMobile.getLinkAccountDetails().getAtmCredLength() + "\n" +
                                "\t}]\n" + "}";
                        Log.e("third", credAllowedString);
                    } else {
                        credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                                "{\n" +
                                "\t\t\"type\": \"OTP\",\n" +
                                "\t\t\"subtype\": \"SMS\",\n" +
                                "\t\t\"dType\": \""+OTP_TYPE+"\",\n" +
                                "\t\t\"dLength\": "+OTP_LENGTH+"\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"MPIN\",\n" +
                                "\t\t\"dType\":\"ALPH | NUM\",\n" +
                                "\t\t\"dLength\": 6\n" +
                                "\t}, {\n" +
                                "\t\t\"type\": \"PIN\",\n" +
                                "\t\t\"subtype\": \"ATMPIN\",\n" +
                                "\t\t\"dType\": \"NUM\",\n" +
                                "\t\t\"dLength\": 4\n" +
                                "\t}]\n" + "}";
                        Log.e("fourth", credAllowedString);
                    }
                }

            } else {
                if (reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() != null
                        && reqRegMobile.getLinkAccountDetails().getCredDataType() != null
                        && reqRegMobile.getLinkAccountDetails().getCredLength() != null) {
                    String D_TYPE = "";
                    if (reqRegMobile.getLinkAccountDetails().getCredDataType().equalsIgnoreCase("Numeric")) {
                        D_TYPE = "NUM";
                    } else {
                        D_TYPE = "ALPH | NUM";
                    }
                    credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                            "{\n" +
                            "\t\t\"type\": \"OTP\",\n" +
                            "\t\t\"subtype\": \"SMS\",\n" +
                            "\t\t\"dType\": \""+OTP_TYPE+"\",\n" +
                            "\t\t\"dLength\": "+OTP_LENGTH+"\n" +
                            "\t}, {\n" +
                            "\t\t\"type\": \"PIN\",\n" +
                            "\t\t\"subtype\": \"" + reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() + "\",\n" +
                            "\t\t\"dType\": \"" + D_TYPE + "\",\n" +
                            "\t\t\"dLength\": " + reqRegMobile.getLinkAccountDetails().getCredLength() + "\n" +
                            "\t}]\n" + "}";
                    Log.e("fifth", credAllowedString);
                } else {
                    credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                            "{\n" +
                            "\t\t\"type\": \"OTP\",\n" +
                            "\t\t\"subtype\": \"SMS\",\n" +
                            "\t\t\"dType\": \""+OTP_TYPE+"\",\n" +
                            "\t\t\"dLength\": "+OTP_LENGTH+"\n" +
                            "\t}, {\n" +
                            "\t\t\"type\": \"PIN\",\n" +
                            "\t\t\"subtype\": \"MPIN\",\n" +
                            "\t\t\"dType\": \"ALPH | NUM\",\n" +
                            "\t\t\"dLength\": 6\n" +
                            "\t}]\n" + "}";
                    Log.e("sixth", credAllowedString);
                }
            }

        } else {
            if (reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() != null
                    && reqRegMobile.getLinkAccountDetails().getCredDataType() != null
                    && reqRegMobile.getLinkAccountDetails().getCredLength() != null) {
                String D_TYPE = "";
                if (reqRegMobile.getLinkAccountDetails().getCredDataType().equalsIgnoreCase("Numeric")) {
                    D_TYPE = "NUM";
                } else {
                    D_TYPE = "ALPH | NUM";
                }
                credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                        "{\n" +
                        "\t\t\"type\": \"OTP\",\n" +
                        "\t\t\"subtype\": \"SMS\",\n" +
                        "\t\t\"dType\": \""+OTP_TYPE+"\",\n" +
                        "\t\t\"dLength\": "+OTP_LENGTH+"\n" +
                        "\t}, {\n" +
                        "\t\t\"type\": \"PIN\",\n" +
                        "\t\t\"subtype\": \"" + reqRegMobile.getLinkAccountDetails().getAccetptedCredSubType() + "\",\n" +
                        "\t\t\"dType\": \"" + D_TYPE + "\",\n" +
                        "\t\t\"dLength\": " + reqRegMobile.getLinkAccountDetails().getCredLength() + "\n" +
                        "\t}]\n" + "}";
                Log.e("seventh", credAllowedString);
            } else {
                credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                        "{\n" +
                        "\t\t\"type\": \"OTP\",\n" +
                        "\t\t\"subtype\": \"SMS\",\n" +
                        "\t\t\"dType\": \""+OTP_TYPE+"\",\n" +
                        "\t\t\"dLength\": "+OTP_LENGTH+"\n" +
                        "\t}, {\n" +
                        "\t\t\"type\": \"PIN\",\n" +
                        "\t\t\"subtype\": \"MPIN\",\n" +
                        "\t\t\"dType\": \"ALPH | NUM\",\n" +
                        "\t\t\"dLength\": 6\n" +
                        "\t}]\n" + "}";
                Log.e("eighth", credAllowedString);
            }
        }


        // Create Configuration
        final JSONObject configuration = new JSONObject();
        ;
        try {
            //TODO Have to add bank name...
            if (reqRegMobile.getBank_name() != null && reqRegMobile.getBank_name().length() > 0)
                configuration.put("payerBankName", reqRegMobile.getBank_name());
            else
                configuration.put("payerBankName", SDKUtils.BANK_NAME);

            configuration.put("backgroundColor", "#FFFFFF");
            Log.i("configuration", configuration.toString());
            //OTP Feature Enable.....
            configuration.put("resendOTPFeature", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Create Salt
        final JSONObject salt = new JSONObject();
        try {
            salt.put("txnId", reqRegMobile.getTxnId());
            salt.put("deviceId", SDKUtils.getDeviceId());
            salt.put("appId", SDKUtils.getAppId());
            salt.put("mobileNumber", SDKUtils.getMobileNumber());
            //  salt.put("payerAddr", balanceEnquiryReq.getVpa());
            Log.i("salt", salt.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String trustStr = null;
        try {
            StringBuilder trustParamBuilder = new StringBuilder(100);
            trustParamBuilder.
                    append(reqRegMobile.getTxnId()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getAppId()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getMobileNumber()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getDeviceId());
            trustStr = TrustCreator.createTrust(trustParamBuilder.toString(), this.token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String newtrust = trustStr;
        // Create Pay Info
        final JSONArray payInfoArray = new JSONArray();
        try {
            JSONObject registeredMobile = new JSONObject();
            registeredMobile.put("name", "mobileNumber");
            registeredMobile.put("value", SDKUtils.getMobileNumber());
            payInfoArray.put(registeredMobile);

            Log.i("payInfo", payInfoArray.toString());
            //intent.putExtra("payInfo", payInfoArray.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /**
         * create a CLRemoteResultReceiver instance to receive the encrypted credential.
         */
        final CLRemoteResultReceiver remoteResultReceiver = new CLRemoteResultReceiver(new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);

                if (resultData != null || resultCode == 2) {
                    if (resultCode == 2) {
                        recallOTP(reqRegMobile);
                    } else {
                        if (resultData.getString("error") != null && resultData.getString("error").equalsIgnoreCase("USER_ABORTED")) {
                            reqRegMobile.getiStateListener().getState(State.IDLE);
                        } else {
                            parseResult(resultData, callback);
                        }
                    }
                } else {
                    reqRegMobile.getiStateListener().getState(State.IDLE);
                }


            }
        });

        ListKeys.populateXmlPayLoad(new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String xmlPayload) {
                clServices.getCredential(keyCode, xmlPayload, credAllowedString,
                        configuration.toString(), salt.toString(), payInfoArray.toString(), newtrust, CLConstants.CL_LANGUAGE.name(), remoteResultReceiver);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                callback.onError(status, errors);
            }
        });

        /**
         * Call get Credential
         */
        /*if(SDKUtils.getXmlPayloadString()!=null && SDKUtils.getXmlPayloadString().length()>0)
        clServices.getCredential(keyCode, SDKUtils.getXmlPayloadString(), credAllowedString,
                configuration.toString(), salt.toString(), payInfoArray.toString(), trustStr, CLConstants.CL_LANGUAGE, remoteResultReceiver);
        else
            getPayloadString();
*/
    }

    /**
     * Return credentials for all type of requests
     */
    public void getCredentialChangeMPIN(final SetCredential setCredential,
                                        final ServiceCallback callback) {

        if (setCredential.getCredAllowed() != null) {
            String D_TYPE = "";
            if (setCredential.getCredAllowed().getDtype().equalsIgnoreCase("Numeric")) {
                D_TYPE = "NUM";
            } else {
                D_TYPE = "ALPH | NUM";
            }

            credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                    "{\n" +
                    "\t\t\"type\": \"PIN\",\n" +
                    "\t\t\"subtype\": \"MPIN\",\n" +
                    "\t\t\"dType\": \"" + D_TYPE + "\",\n" +
                    "\t\t\"dLength\": " + setCredential.getCredAllowed().getDlemgth() + "\n" +
                    "\t}, {\n" +
                    "\t\t\"type\": \"PIN\",\n" +
                    "\t\t\"subtype\": \"NMPIN\",\n" +
                    "\t\t\"dType\": \"" + D_TYPE + "\",\n" +
                    "\t\t\"dLength\": " + setCredential.getCredAllowed().getDlemgth() + "\n" +
                    "\t}]\n" + "}";

            Log.e("credAllowedString", credAllowedString);

            setCredential.setCredAllowed(null);
        } else {
            credAllowedString = "{\n" + "\t\"CredAllowed\": [" +
                    "{\n" +
                    "\t\t\"type\": \"PIN\",\n" +
                    "\t\t\"subtype\": \"MPIN\",\n" +
                    "\t\t\"dType\": \"ALPH | NUM\",\n" +
                    "\t\t\"dLength\": 6\n" +
                    "\t}, {\n" +
                    "\t\t\"type\": \"PIN\",\n" +
                    "\t\t\"subtype\": \"NMPIN\",\n" +
                    "\t\t\"dType\": \"NUM\",\n" +
                    "\t\t\"dLength\": 6\n" +
                    "\t}]\n" + "}";
            Log.e("credAllowedDefault", credAllowedString);
        }


        // Create Configuration
        final JSONObject configuration = new JSONObject();
        ;
        try {
            //TODO Have to add bank name...
            Log.d("Common Library", "Bank Name:" + SDKUtils.BANK_NAME);
            if (setCredential.getBank_name() != null && setCredential.getBank_name().length() > 0)
                configuration.put("payerBankName", setCredential.getBank_name());
            else
                configuration.put("payerBankName", SDKUtils.BANK_NAME);
            configuration.put("backgroundColor", "#FFFFFF");
            Log.i("configuration", configuration.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Create Salt
        final JSONObject salt = new JSONObject();
        try {
            salt.put("txnId", setCredential.getTxnId());
            salt.put("deviceId", SDKUtils.getDeviceId());
            salt.put("appId", SDKUtils.getAppId());
            salt.put("mobileNumber", SDKUtils.getMobileNumber());
            //  salt.put("payerAddr", balanceEnquiryReq.getVpa());
            Log.i("salt", salt.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String trustStr = null;
        try {
            StringBuilder trustParamBuilder = new StringBuilder(100);
            trustParamBuilder.
                    append(setCredential.getTxnId()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getAppId()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getMobileNumber()).append(CLConstants.SALT_DELIMETER)
                    .append(SDKUtils.getDeviceId());
            trustStr = TrustCreator.createTrust(trustParamBuilder.toString(), this.token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String newtrust = trustStr;
        // Create Pay Info
        final JSONArray payInfoArray = new JSONArray();
        try {
            JSONObject registeredMobile = new JSONObject();
            registeredMobile.put("name", "mobileNumber");
            registeredMobile.put("value", SDKUtils.getMobileNumber());
            payInfoArray.put(registeredMobile);

            Log.i("payInfo", payInfoArray.toString());
            //intent.putExtra("payInfo", payInfoArray.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /**
         * create a CLRemoteResultReceiver instance to receive the encrypted credential.
         */
        final CLRemoteResultReceiver remoteResultReceiver = new CLRemoteResultReceiver(new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);
                if (resultData != null) {
                    if (resultData.getString("error") != null && resultData.getString("error").equalsIgnoreCase("USER_ABORTED")) {
                        setCredential.getiStateListener().getState(State.IDLE);
                    } else {
                        parseResult(resultData, callback);
                    }

                } else {
                    setCredential.getiStateListener().getState(State.IDLE);
                }
            }
        });

        ListKeys.populateXmlPayLoad(new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String xmlPayload) {
                clServices.getCredential(keyCode, xmlPayload, credAllowedString,
                        configuration.toString(), salt.toString(), payInfoArray.toString(), newtrust, CLConstants.CL_LANGUAGE.name(), remoteResultReceiver);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                callback.onError(status, errors);
            }
        });

        /**
         * Call get Credential
         */
        /*if(SDKUtils.getXmlPayloadString()!=null && SDKUtils.getXmlPayloadString().length()>0)
        clServices.getCredential(keyCode, SDKUtils.getXmlPayloadString(), credAllowedString,
                configuration.toString(), salt.toString(), payInfoArray.toString(), trustStr, CLConstants.CL_LANGUAGE, remoteResultReceiver);
        else
            getPayloadString();
*/
    }

    /**
     * Parse the output from Get Credential Service of Common Library
     *
     * @param data bundle containing the output
     */
    private static void parseResult(Bundle data, ServiceCallback callback) {
        String errorMsgStr = data.getString("error");
        if (errorMsgStr != null && !errorMsgStr.isEmpty()) {
            Log.d("Error:", errorMsgStr);
            try {
                JSONObject error = new JSONObject(errorMsgStr);
                String errorCode = error.getString("errorCode");
                String errorText = error.getString("errorText");
                /*Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(),
                        errorCode + ":" + errorText, Toast.LENGTH_LONG)
                        .show();*/
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onError(WebServiceStatus.FAILURE, null);
            }
            return;
        }
        HashMap<String, String> credListHashMap = (HashMap<String, String>) data
                .getSerializable("credBlocks");
        List<Cred> creds = new ArrayList<>();
        for (String cred : credListHashMap.keySet()) {
            // This will return the list of field name e.g mpin,otp etc...
            try {
                JSONObject credBlock = new JSONObject(
                        credListHashMap.get(cred));
                Log.i("enc_msg",
                        credBlock.getJSONObject("data").getString(
                                "encryptedBase64String"));

                Cred credObject = new Cred();
                credObject.setSubtype(credBlock.getString("subType"));
                credObject.setType(credBlock.getString("type"));
                Data datas = new Data();
                datas.setKi(credBlock.getJSONObject("data").getString("ki"));
                datas.setCode(credBlock.getJSONObject("data").getString("code"));
                datas.setEncryptedBase64String(credBlock.getJSONObject("data").getString(
                        "encryptedBase64String"));
                credObject.setData(datas);

                creds.add(credObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        callback.onSuccess(creds.toArray(new Cred[0]));
    }


    /**
     * Integrate the library for Registration/Rotation.
     *
     * @param type "initial" for registartion, "rotate" for rotation
     * @throws Exception
     */
    private void integrateLibrary(final String type, final Context applicationContext,
                                  final ServiceCallback<CommonLibrary> callback) throws Exception {
        get_token_hit_count = 1;
        final String appId = applicationContext.getPackageName();
        final String mobile = SDKUtils.getMobileNumber();
        final String deviceId = SDKUtils.getDeviceId();
        String challenge = getChallenge(type, deviceId, appId, mobile);
        if (challenge == null) {
            Log.d(applicationContext.getClass().getName(), "Error while getting challenge.");
            return;
        }

        String temp_challenge = "";
        temp_challenge = deviceId + "|" + appId + "|" + mobile + "|";

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setTokenRequestType(TokenRequestType.INITIAL);
        tokenRequest.setChallenge(temp_challenge + challenge);
        tokenRequest.setTxnId("");

        if (SDKUtils.isNetworkAvailable(UpiSDKContext.getInstance().getApplicationContext())) {
            UppServices.getToken(tokenRequest, new ServiceCallback<TokenResponse>(TokenResponse.class) {
                @Override
                public void onSuccess(TokenResponse result) {
                    if (result != null) {
                        String token_encoded = "";
                        token_encoded = result.getTokenEncoded();
                        if (token_encoded != null && token_encoded.length() > 0) {
                            byte[] tokenBytes = Base64.decode(token_encoded, Base64.NO_WRAP);
                            String token_hex = "";
                            token_hex = cryptLib.byteArrayToHex(tokenBytes);
                            Log.e("token_hex", token_hex);
                            SDKUtils.TOKEN = token_hex;
                            CommonLibrary.this.token = token_hex;
                            //ApplicationClassUpiTest.getSharedPreference().setToken(token_hex);
                            if (!token_hex.isEmpty()) {
                                String hmac = populateHMAC(appId, mobile, token_hex, deviceId);
                                clInitialized = clServices.registerApp(appId, mobile, deviceId, hmac);
                                if (!clInitialized) {
                                    Log.d(this.getClass().getName(), "Error while registering app");
                                    callback.onError(WebServiceStatus.FAILURE, null);
                                    return;
                                } else {
                                    if (!clInitialized) {
                                        Toast.makeText(applicationContext, "App not registered with CL", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(applicationContext, "App is registered with CL", Toast.LENGTH_LONG).show();
                                        callback.onSuccess(CommonLibrary.this);
                                    }
                                }
                            }
                        } else {
                            repeatTokenHit(type, result, appId, mobile, deviceId, applicationContext, callback);
                        }
                    }

                }

                @Override
                public void onError(WebServiceStatus status, List<Error> errors) {

                }
            });
        } else {
            Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * @param deviceId
     * @param type
     * @param appId
     * @param mobile
     * @return
     */
    private String getChallenge(String type, String deviceId,
                                String appId, String mobile) {
        String request_challenge = null;
        request_challenge = clServices.getChallenge(type, deviceId);
        Log.d("challenge", request_challenge);
        return request_challenge;

    }

    /**
     * Populate HMac for app registration
     *
     * @param app_id
     * @param mobile
     * @param token
     * @param deviceId
     * @return
     */
    private String populateHMAC(String app_id, String mobile, String token,
                                String deviceId) {
        String hmac = null;
        try {

            String message = app_id + "|" + mobile + "|" + deviceId;
            Log.d("PSP Hmac Msg", message);
            //String hashMessage = cryptLib.SHA256(message);

            // cryptLib.hexStringToByteArray(token);
            byte[] tokenBytes = cryptLib.hexStringToByteArray(token);
            byte[] hmacBytes = cryptLib.encrypt(
                    cryptLib.SHA256(message),
                    tokenBytes);
            hmac = Base64.encodeToString(hmacBytes, Base64.NO_WRAP);
            Log.d("PSP Hmac", hmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hmac;
    }

/*    private static void getPayloadString(){

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setTokenRequestType(TokenRequestType.LIST_KEYS);
        tokenRequest.setChallenge("");
        tokenRequest.setTxnId("");

        UppServices.getListKeys(tokenRequest, new ServiceCallback<TokenResponse[]>(TokenResponse[].class) {
            @Override
            public void onSuccess(TokenResponse[] result) {
                if (result != null && result.length > 0) {
                    SDKUtils.XML_PAYLOAD_STRING=result[0].getTokenEncoded();
                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(),errors.get(0).getErrorCode(),Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    private String txn_id = "";

    private void repeatTokenHit(final String type, TokenResponse result, final String appId, final String mobile, final String deviceId, final Context applicationContext, final ServiceCallback<CommonLibrary> callback) {
        final TokenRequest tokenRequest = new TokenRequest();
        if (TOKEN_INIT_MODE.equalsIgnoreCase(type)) {
            tokenRequest.setTokenRequestType(TokenRequestType.INITIAL);
        } else {
            tokenRequest.setTokenRequestType(TokenRequestType.ROTATE);
        }
        if (result.getTxnId() != null && get_token_hit_count == 1) {
            txn_id = result.getTxnId();
        }
        tokenRequest.setTxnId(txn_id);
        get_token_hit_count++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UppServices.getToken(tokenRequest, new ServiceCallback<TokenResponse>(TokenResponse.class) {
                    @Override
                    public void onSuccess(TokenResponse result) {
                        String token_encoded = "";
                        token_encoded = result.getTokenEncoded();
                        if (token_encoded != null && token_encoded.length() > 0) {
                            byte[] tokenBytes = Base64.decode(token_encoded, Base64.NO_WRAP);
                            String token_hex = "";
                            token_hex = cryptLib.byteArrayToHex(tokenBytes);
                            Log.e("token_hex", token_hex);
                            SDKUtils.TOKEN = token_hex;
                            CommonLibrary.this.token = token_hex;
                            //ApplicationClassUpiTest.getSharedPreference().setToken(token_hex);
                            if (!token_hex.isEmpty()) {
                                String hmac = populateHMAC(appId, mobile, token_hex, deviceId);
                                clInitialized = clServices.registerApp(appId, mobile, deviceId, hmac);
                                if (!clInitialized) {
                                    Log.d(this.getClass().getName(), "Error while registering app");
                                    callback.onError(WebServiceStatus.FAILURE, null);
                                    return;
                                } else {
                                    if (!clInitialized) {
                                        Toast.makeText(applicationContext, "App not registered with CL", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(applicationContext, "App is registered with CL", Toast.LENGTH_LONG).show();
                                        callback.onSuccess(CommonLibrary.this);
                                    }
                                }
                            }
                        } else {
                            if (get_token_hit_count <= 6) {
                                repeatTokenHit(type, result, appId, mobile, deviceId, applicationContext, callback);
                            } else {
                                callback.onError(WebServiceStatus.FAILURE, null);
                            }

                        }

                    }

                    @Override
                    public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                        callback.onError(status, errors);
                    }
                });
            }
        }, 10000);
    }

    public void recallOTP(ReqRegMobile reqRegMobile) {
        RequestOtp request = new RequestOtp();
        request.setPaymentTxnId(reqRegMobile.getTxnId());
        request.setOperation("GENERATE");
        Payer payer = new Payer();
        if (reqRegMobile.getAccNo() != null)
            payer.setBankAcNumber(reqRegMobile.getAccNo());
        payer.setPayerAcVpa(UpiSDKContext.getInstance().getUserDefaultVpa());
        payer.setPayerAcNickName(UpiSDKContext.getInstance().getUserId());
        payer.setMobile(UpiSDKContext.getInstance().getUserMobile());
        payer.setIfsc(reqRegMobile.getIfsc());
        request.setDeviceDetails(SDKUtils.getDeviceDetails(HmacGenerationPolicy.GENERAL));
        request.setPayer(payer);
        UppServices.requestOTP(request, new ServiceCallback<Void>(Void.class) {
            @Override
            public void onSuccess(Void result) {
                Log.d("OTP Subflow", "OTP Request Success");

            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                Log.d("OTP Subflow", "OTP Request Failure");
//                if (errors != null && !errors.isEmpty()) {
//                    chain.breakChain(errors.get(0).getErrorCode(), null);
//                } else {
//                    chain.breakChain(SDKErrorCodes.ERR00052.name(), null);
//                }
            }
        });
    }


}
