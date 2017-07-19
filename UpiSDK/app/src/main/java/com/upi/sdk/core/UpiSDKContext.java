package com.upi.sdk.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;

import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.rssoftware.upiint.schema.ConfigElement;
import com.rssoftware.upiint.schema.Device;
import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.ForgetMpin;
import com.rssoftware.upiint.schema.IMessageListener;
import com.rssoftware.upiint.schema.InputChangeMPIN;
import com.rssoftware.upiint.schema.InputTxnHistoryDetails;
import com.rssoftware.upiint.schema.LinkAccountDetails;
import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.Mode;
import com.rssoftware.upiint.schema.Notifications;
import com.rssoftware.upiint.schema.OtpServiceDetails;
import com.rssoftware.upiint.schema.Payer;
import com.rssoftware.upiint.schema.PspBankList;
import com.rssoftware.upiint.schema.RaiseDispute;
import com.rssoftware.upiint.schema.ReqRegMobile;
import com.rssoftware.upiint.schema.RequestValAddress;
import com.rssoftware.upiint.schema.RespRegMobile;
import com.rssoftware.upiint.schema.ResponseSetCre;
import com.rssoftware.upiint.schema.ResponseValAddress;
import com.rssoftware.upiint.schema.SetCredential;
import com.rssoftware.upiint.schema.State;
import com.rssoftware.upiint.schema.TransactionHistory;
import com.rssoftware.upiint.schema.TxnHistoryDetails;
import com.rssoftware.upiint.schema.UserEntityType;
import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.CommonLibrary;
import com.upi.sdk.CryptLib;
import com.upi.sdk.domain.APICallback;
import com.upi.sdk.domain.BalanceEnquiryRequest;
import com.upi.sdk.domain.BalanceEnquiryResponse;
import com.upi.sdk.domain.BankAccountBean;
import com.upi.sdk.domain.BeneficiaryType;
import com.upi.sdk.domain.ChangePassword;
import com.upi.sdk.domain.InListAddedBeneficiaryQuery;
import com.upi.sdk.domain.InListLinkAccountQuery;
import com.upi.sdk.domain.InListLinkAccountQueryByMobile;
import com.upi.sdk.domain.InListVPAQuery;
import com.upi.sdk.domain.InPayRequest;
import com.upi.sdk.domain.InPaymentAuthorization;
import com.upi.sdk.domain.InPendingTxnQuery;
import com.upi.sdk.domain.InTxnHistoryQuery;
import com.upi.sdk.domain.InputActivateVPA;
import com.upi.sdk.domain.InputAddBankAccount;
import com.upi.sdk.domain.InputAddDefaultVPA;
import com.upi.sdk.domain.InputAddUpdateUserLimit;
import com.upi.sdk.domain.InputAddVPA;
import com.upi.sdk.domain.InputBeneficiary;
import com.upi.sdk.domain.InputChannelId;
import com.upi.sdk.domain.InputConfigParam;
import com.upi.sdk.domain.InputDeleteBankAccount;
import com.upi.sdk.domain.InputDeleteBeneficiary;
import com.upi.sdk.domain.InputDeleteSchedule;
import com.upi.sdk.domain.InputDeleteVPA;
import com.upi.sdk.domain.InputNotification;
import com.upi.sdk.domain.InputSchedule;
import com.upi.sdk.domain.InputServiceCharge;
import com.upi.sdk.domain.InputUpdateBeneficiary;
import com.upi.sdk.domain.InputUpdateVPA;
import com.upi.sdk.domain.InputcheckVPAAvailability;
import com.upi.sdk.domain.OutPayRequest;
import com.upi.sdk.domain.OutPendingTxn;
import com.upi.sdk.domain.Schedule;
import com.upi.sdk.domain.ServiceChargeResult;
import com.upi.sdk.domain.UpdatePassword;
import com.upi.sdk.domain.UserInfo;
import com.upi.sdk.domain.UserProfile;
import com.upi.sdk.domain.VaeDetails;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.errors.UPISDKException;
import com.upi.sdk.processflow.ProcessFlow;
import com.upi.sdk.processflow.bankAccount.AddBankAccConverterSubflow;
import com.upi.sdk.processflow.bankAccount.AddUpdateUserLimitConverterSubflow;
import com.upi.sdk.processflow.bankAccount.AddUpdateUserLimitPopulatorSubflow;
import com.upi.sdk.processflow.bankAccount.BalanceEnquiryCredentialCaptureAuthSubflow;
import com.upi.sdk.processflow.bankAccount.BalanceEnquiryRequestConverterSubflow;
import com.upi.sdk.processflow.bankAccount.BalanceEnquirySubflow;
import com.upi.sdk.processflow.bankAccount.BankAccAdditionSubflow;
import com.upi.sdk.processflow.bankAccount.BankAccAdditionSubflowbyMobile;
import com.upi.sdk.processflow.bankAccount.BankAccDeletionSubflow;
import com.upi.sdk.processflow.bankAccount.BankAccListingSubflow;
import com.upi.sdk.processflow.bankAccount.BankAccListingSubflowByMobile;
import com.upi.sdk.processflow.bankAccount.BankAccListingSubflowByMobileFinal;
import com.upi.sdk.processflow.bankAccount.ChangeMpinCredentialCaptureAuthSubflow;
import com.upi.sdk.processflow.bankAccount.ChangeMpinSubflow;
import com.upi.sdk.processflow.bankAccount.DeleteBankAccConverterSubflow;
import com.upi.sdk.processflow.bankAccount.FetchBankPspSubflow;
import com.upi.sdk.processflow.bankAccount.ForgetMpinSubflow;
import com.upi.sdk.processflow.bankAccount.ListBankAccConverterSubflow;
import com.upi.sdk.processflow.bankAccount.ListBankAccConverterSubflowByMobile;
import com.upi.sdk.processflow.bankAccount.RegMobCredentialCaptureAuthSubflow;
import com.upi.sdk.processflow.bankAccount.RegMobSubflow;
import com.upi.sdk.processflow.bankAccount.RegMobSubflowFinal;
import com.upi.sdk.processflow.beneficiary.AddBeneficiaryConverterSubflow;
import com.upi.sdk.processflow.beneficiary.AddBeneficiarySubflow;
import com.upi.sdk.processflow.beneficiary.BeneficiaryDeleteSubflow;
import com.upi.sdk.processflow.beneficiary.BeneficiaryListingSubflow;
import com.upi.sdk.processflow.beneficiary.BeneficiaryUpdationSubflow;
import com.upi.sdk.processflow.beneficiary.DeleteBeneficiaryConverterSubflow;
import com.upi.sdk.processflow.beneficiary.ListBeneficiaryConverterSubflow;
import com.upi.sdk.processflow.beneficiary.UpdateBeneficiaryConverterSubflow;
import com.upi.sdk.processflow.initialization.ActivateVPASubflow;
import com.upi.sdk.processflow.initialization.CLInitSubflow;
import com.upi.sdk.processflow.initialization.ChangePasswordSubflow;
import com.upi.sdk.processflow.initialization.ConfigParamConverterSubflow;
import com.upi.sdk.processflow.initialization.ConfigParamSubflow;
import com.upi.sdk.processflow.initialization.DeActivateVPASubflow;
import com.upi.sdk.processflow.initialization.DeRegisterUserSubflow;
import com.upi.sdk.processflow.initialization.LoginSubflow;
import com.upi.sdk.processflow.initialization.RegisterAppSubflow;
import com.upi.sdk.processflow.initialization.RequestOtpForForgetPINSubflow;
import com.upi.sdk.processflow.initialization.RequestOtpSubflow;
import com.upi.sdk.processflow.initialization.SendOtpSubflow;
import com.upi.sdk.processflow.initialization.UpdatePINSubflow;
import com.upi.sdk.processflow.initialization.UserRegistrationSubflow;
import com.upi.sdk.processflow.initialization.ValidateMobileNumberSubflow;
import com.upi.sdk.processflow.initialization.ValidateOTPForForgetPINSubflow;
import com.upi.sdk.processflow.schedule.AddSchedulePopulatorSubflow;
import com.upi.sdk.processflow.schedule.DeleteSchedulePopulatorSubflow;
import com.upi.sdk.processflow.schedule.ListSchedulePopulatorSubflow;
import com.upi.sdk.processflow.schedule.UpdateSchedulePopulatorSubflow;
import com.upi.sdk.processflow.transaction.CalculateServiceChargeSubflow;
import com.upi.sdk.processflow.transaction.ChannelIdPopulatorSubflow;
import com.upi.sdk.processflow.transaction.CredentialCaptureAuthSubflow;
import com.upi.sdk.processflow.transaction.CredentialCaptureSubflow;
import com.upi.sdk.processflow.transaction.InPayReqConverterSubflow;
import com.upi.sdk.processflow.transaction.NotificationsPopulatorSubflow;
import com.upi.sdk.processflow.transaction.PaymentAuthRequestConverterSubflow;
import com.upi.sdk.processflow.transaction.PaymentAuthorizationSubflow;
import com.upi.sdk.processflow.transaction.PaymentInitiatorSubflow;
import com.upi.sdk.processflow.transaction.PendingTxnListPopulatorSubflow;
import com.upi.sdk.processflow.transaction.RaiseDisputeSubflow;
import com.upi.sdk.processflow.transaction.TransactionHistoryDetailsSubflow;
import com.upi.sdk.processflow.transaction.TransactionHistoryDetailsSubflowByRefId;
import com.upi.sdk.processflow.transaction.TransactionHistoryPopulatorSubflow;
import com.upi.sdk.processflow.transaction.TransactionHistoryPopulatorSubflowByRefId;
import com.upi.sdk.processflow.transaction.TransactionHistoryPopulatorSubflowByVPA;
import com.upi.sdk.processflow.transaction.TransactionIdGeneratorSubflow;
import com.upi.sdk.processflow.transaction.VaeDetailsSubflow;
import com.upi.sdk.processflow.vpa.AddVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.CheckVpaAvailabilityConverterSubflow;
import com.upi.sdk.processflow.vpa.CheckVpaAvailabilitySubflow;
import com.upi.sdk.processflow.vpa.DeleteVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.ListVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.UpdateVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.ValidateVpaSubflow;
import com.upi.sdk.processflow.vpa.ValidateVpaSubflowFinal;
import com.upi.sdk.processflow.vpa.VpaAdditionSubflow;
import com.upi.sdk.processflow.vpa.VpaDeletionSubflow;
import com.upi.sdk.processflow.vpa.VpaPopulatorSubflow;
import com.upi.sdk.processflow.vpa.VpaUpdationSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.WebServiceStatus;
import com.upi.sdk.utils.CLConstants;
import com.upi.sdk.utils.CLLanguage;
import com.upi.sdk.utils.Prefs;
import com.upi.sdk.utils.SDKUtils;

import org.npci.upi.security.services.CLServices;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This method contain the basic components which are needed for communicating with
 * UPI Intgeration Manager. The components are fetched from the server side at the time
 * of initialization
 * Created by NeeloyG on 22-04-2016.
 */
public class UpiSDKContext {

    private static UpiSDKContext _context = null;
    private static ReentrantLock lock = new ReentrantLock();
    private  Context applicationContext;
    private String customerRefId;
    private String mobileNumber;
    private static double latitude = 0;
    private static double longitude = 0;
    private static boolean initialized = false;
    private static UserProfile user = new UserProfile();
    private static CommonLibrary commonLibrary = null;
    public static Prefs prefs;
    private static String message = "";


    /**
     * The constructor which internally calls the init() method for initializing the component
     * from the backend
     *
     * @param context
     * @param customerRefId
     */
    private UpiSDKContext(Context context, String customerRefId, String passphrase)
            throws UPISDKException {
        this.applicationContext = context;

//        if (UPPSDKConstants.CURRENT_CHANNEL_TYPE == ChannelType.MOBILE) {
//            user.setUserId(customerRefId);
//        } else {
//            user.setBankCustomerId(customerRefId);
//        }
        user.setUserId(customerRefId);
        user.setUserPassword(passphrase);
        prefs = new Prefs(context);

    }

    private UpiSDKContext(Context context, InputAddDefaultVPA inDefVpa) {
        this.applicationContext = context;
        prefs = new Prefs(context);
        if (!TextUtils.isEmpty(inDefVpa.getUserVpa())) {
            user.setUserVpa(inDefVpa.getUserVpa());
            user.setUserId(inDefVpa.getUserVpa().substring(0, inDefVpa.getUserVpa().indexOf('@')));
        }
        user.setBankCustomerId(inDefVpa.getCutomerRefId());
        String password = "";
        password = SDKUtils.getEncryptedPassword(inDefVpa.getPassphrase());
        user.setUserPassword(password);
        if (inDefVpa.getUserMobile() != null && inDefVpa.getUserMobile().length() > 0)
            user.setUserMobile(inDefVpa.getUserMobile());
        user.setUserName(inDefVpa.getUserName());
        user.setUserEmail(inDefVpa.getUserEmail());
        user.setUserMaxPayLimit(BigDecimal.valueOf(Double.valueOf(
                inDefVpa.getUserMaxPayLimit())));
        user.setUserAddress(inDefVpa.getUserAddress());
        user.setIdentities(inDefVpa.getIdentities());
        user.setSecurityAnswers(inDefVpa.getSecurityAnswers());

    }

    public String getSessionToken() {
        return SDKUtils.getUsedK0();
    }

    /**
     * Initialize Configuration
     */
    public static void initConfigurationParametres(Context context, InputConfigParam qry, final APICallback<ConfigElement> callback)
            throws Exception {
//        if(SDKUtils.isRooted()) {
//            Toast.makeText(context,"SDK is not accessible on this device",Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(!SDKUtils.isSimSupport(context)) {
//            Toast.makeText(context,"SDK is not accessible on this device",Toast.LENGTH_SHORT).show();
//            return;
//        }
        ProcessFlow.create(qry, callback)
                .addNext(new ConfigParamConverterSubflow())
                .addNext(new ConfigParamSubflow())
                .execute();
    }

    public static void setGcmIDforPushNotifications(String gcmId) {
        SDKUtils.GCM_ID = gcmId;
    }

    public static void setLanguageforCL(CLLanguage clLanguage) {
        CLConstants.CL_LANGUAGE = clLanguage;
    }


    /**
     * Send SMS
     */
    public static void sentSMS(Context context, final IMessageListener iMessageListener, String subscriptionId) {
        try {

            String temp_message = "";
            message = SDKUtils.getDeviceId(context) + "|" + System.currentTimeMillis();
            Log.e("message", message);
            CryptLib cryptLib = new CryptLib();
            try {
                byte[] calculatedHash = message.getBytes("UTF-8");
                byte[] encryptedData = cryptLib.encrypt(calculatedHash,
                        cryptLib.hexStringToByteArray(SDKUtils.K0HEX));
                message = cryptLib.byteArrayToHex(encryptedData);
                Log.e("data to hex ", message);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (SDKUtils.PSP != null && SDKUtils.PSP.length() > 0) {
                if (SDKUtils.PSP.equalsIgnoreCase("idbi")) {
                    temp_message="UPIREG "+message;
                   // temp_message = "UPITEST " + message;
                } else if (SDKUtils.PSP.equalsIgnoreCase("lvb")) {

                    temp_message = "LVBUPI " + message;
                    //  temp_message="LVBUPAAY "+message;


                }
            }

            if (subscriptionId != null && subscriptionId.length() > 0) {
                String SENT = "SMS_SENT";
                String DELIVERED = "SMS_DELIVERED";
                PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
                PendingIntent deliveryIntent = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);
                if (temp_message != null && temp_message.length() > 0) {
                    SmsManager.getSmsManagerForSubscriptionId(Integer.parseInt(subscriptionId)).sendTextMessage(SDKUtils.VMN_NUMBER, null, temp_message, sentIntent, deliveryIntent);
                } else {
                    SmsManager.getSmsManagerForSubscriptionId(Integer.parseInt(subscriptionId)).sendTextMessage(SDKUtils.VMN_NUMBER, null, message, sentIntent, deliveryIntent);
                }

            } else {
                SmsManager smsManager = SmsManager.getDefault();
                if (temp_message != null && temp_message.length() > 0) {
                    smsManager.sendTextMessage(SDKUtils.VMN_NUMBER, null, temp_message, null, null);
                } else {
                    smsManager.sendTextMessage(SDKUtils.VMN_NUMBER, null, message, null, null);
                }

            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    iMessageListener.getMessage(message);
                }
            }, 5000);

        } catch (Exception e) {
            iMessageListener.getMessage("SMS sending failed, please try again.");
            e.printStackTrace();
        }
    }

    /**
     * Validate Mobile Number
     */
    public static void validateMobileNUmber(String message, final APICallback<String> callback) {
        SDKUtils.VMN_MESSAGE = "/" + message;
        ProcessFlow.create(message, callback)
                .addNext(new ValidateMobileNumberSubflow())
                .execute();

    }

    /**
     * OTP for forget PIN
     */

    public static void requestOTPforForgetPIN(String userId, final APICallback<String> callback) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        ProcessFlow.create(userInfo, callback)
                .addNext(new RequestOtpForForgetPINSubflow())
                .execute();

    }

    /**
     * OTP for forget PIN
     */
    public static void requestOTPforForgetPIN(String userId, Context context, final APICallback<String> callback) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        if (context != null) {
            userInfo.setContext(context);
        }
        ProcessFlow.create(userInfo, callback)
                .addNext(new RequestOtpForForgetPINSubflow())
                .execute();

    }

    /**
     * Validate OTP for forget PIN
     */

    public static void validateOTPforForgetPIN(OtpServiceDetails otpServiceDetails, final APICallback<String> callback) {
        String OTP = "";
        if (otpServiceDetails.getOtpVal() != null && otpServiceDetails.getOtpVal().length() > 0)
            OTP = SDKUtils.getEncryptedOTP(otpServiceDetails.getOtpVal());
        otpServiceDetails.setOtpVal(OTP);
        ProcessFlow.create(otpServiceDetails, callback)
                .addNext(new ValidateOTPForForgetPINSubflow())
                .execute();

    }


    /**
     * Update PIN
     */

    public static void updatePIN(UpdatePassword otpServiceDetails, final APICallback<String> callback) {
        String new_pass = "";
        if (otpServiceDetails.getUserPassword() != null && otpServiceDetails.getUserPassword().length() > 0)
            new_pass = SDKUtils.getEncryptedPassword(otpServiceDetails.getUserPassword());
        otpServiceDetails.setUserPassword(new_pass);
        ProcessFlow.create(otpServiceDetails, callback)
                .addNext(new UpdatePINSubflow())
                .execute();

    }

    /**
     * Return SDK Version
     */

    public static String getSDKVersion() {
        return SDKUtils.getSDKVersion();
    }

    /**
     * The method needs to be called for initializing the SDK
     *
     * @param context
     * @param customerRefId
     * @throws UPISDKException
     */
    private static void initialize(Context context, String customerRefId,
                                   String passphrase, APICallback<UPIPay> initCallback) throws UPISDKException {
        if (context == null
                || TextUtils.isEmpty(customerRefId)
                || TextUtils.isEmpty(passphrase)) {
            throw new UPISDKException(SDKErrorCodes.ERR00098);
        }

        if (UpiSDKContext.isInitialized()) {
            Log.d("APP-SDK", "SDK already initialized");
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        } else {
            synchronized (lock) {
                passphrase = SDKUtils.getEncryptedPassword(passphrase);
                _context = new UpiSDKContext(context, customerRefId, passphrase);
                Device device = new Device();
                device.setUserId(_context.getUserId());
                device.setDeviceId(SDKUtils.getDeviceId());
                device.setDeviceFingerprint(SDKUtils.getDeviceFingerprint());
                _context.user.setDevice(device);
                _context.init(initCallback);
            }
        }

    }

    private static void initializeForRegistration(Context context, InputAddDefaultVPA input,
                                                  APICallback<UPIPay> callback)
            throws UPISDKException {
        if (context == null
                || input == null
                || callback == null) {
            throw new UPISDKException(SDKErrorCodes.ERR00098);
        }

        if (UpiSDKContext.isInitialized()) {
            Log.d("APP-SDK", "SDK already initialized");
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        } else {
            synchronized (lock) {
                _context = new UpiSDKContext(context, input);
                Device device = new Device();
                device.setUserId(_context.getUserId());
                device.setDeviceId(SDKUtils.getDeviceId());
                device.setDeviceFingerprint(SDKUtils.getDeviceFingerprint());
                _context.user.setDevice(device);
                _context.initForRegistration(input, callback);
            }
        }
    }

    /**
     * Checks whether the component has been initialized or not
     *
     * @return true, if initialized; false, otherwise
     */
    public static boolean isInitialized() {
        return (_context != null && initialized);
    }

    /**
     * Get the application context of the parent app
     *
     * @return
     */
    public Context getApplicationContext() {
        return applicationContext;
    }

    public static UpiSDKContext getInstance() {
        return _context;
    }

    public CommonLibrary getCommonLibrary() {
        return this.commonLibrary;
    }


    /**
     * @param initCallback
     * @throws UPISDKException
     */
    private void init(final APICallback<UPIPay> initCallback) throws UPISDKException {
        if (user.getUserVpa() != null && user.getUserVpa().length() > 0)
            user.setUserVpa(null);
        if (user.getClToken() != null && user.getClToken().length() > 0)
            user.setClToken(null);
        if (user.getLastLoginTs() != null && user.getLastLoginTs().length() > 0)
            user.setLastLoginTs(null);
        if (user.getUserMobile() != null && user.getUserMobile().length() > 0)
            user.setUserMobile(null);
        if (user.getUserName() != null && user.getUserName().length() > 0)
            user.setUserName(null);
        if (user.getUserStatus() != null && user.getUserStatus().length() > 0)
            user.setUserStatus(null);
        if (user.getUserVpa() != null && user.getUserVpa().length() > 0)
            user.setUserVpa(null);
        if (user.getClTokenRegistrationDate() != null)
            user.setClTokenRegistrationDate(null);
        if (user.getAggrCode() != null)
            user.setAggrCode(null);

        if (user.getMrchCode() != null)
            user.setMrchCode(null);

        ProcessFlow.create()
                .addNext(new LoginSubflow(user, new ServiceCallback<Boolean>(Boolean.class) {

                    @Override
                    public void onSuccess(Boolean result) {
                        Log.e("result", String.valueOf(result));
                        if (!result) {
                            initCallback.onFailure(SDKErrorCodes.ERR00100.name(), null);
                        }
                        initialized = result;
                        initCallback.onSuccess(new UPIPay(UpiSDKContext.this));
                    }

                    @Override
                    public void onError(WebServiceStatus status, List<Error> errors) {
                        if (errors != null && !errors.isEmpty()) {
                            initCallback.onFailure(errors.get(0).getErrorCode(), null);
                        } else {
                            initCallback.onFailure(SDKErrorCodes.ERR00100.name(), null);
                        }

                    }
                }))
//                .addNext(new RegisterAppSubflow(user, new ServiceCallback<Boolean>(Boolean.class) {
//                    @Override
//                    public void onSuccess(Boolean result) {
//                        initialized = result;
//                        // TODO Start Common Library initialization in the background
//
//                        /// send success status to user login
//                        initCallback.onSuccess(new UPIPay(UpiSDKContext.this));
//                    }
//
//                    @Override
//                    public void onError(WebServiceStatus status, List<Error> errors) {
//
//                        if (errors != null && !errors.isEmpty()) {
//                            initCallback.onFailure(UPIErrorCode.valueOfServiceError(errors.get(0).getErrorCode()));
//                        } else {
//                            initCallback.onFailure(UPIErrorCode.REGISTRATION_ERROR);
//                        }
//                    }
//                }))
                .addNext(new CLInitSubflow(user, new ServiceCallback<CommonLibrary>(CommonLibrary.class) {
                    @Override
                    public void onSuccess(CommonLibrary result) {
                        commonLibrary = result;
                    }

                    @Override
                    public void onError(WebServiceStatus status, List<Error> errors) {
                        Log.d("UPI SDK Context", "Common Library has not been initialized");
                        commonLibrary = null;
                    }
                }, false))
                .execute();

    }

    /**
     * @param initCallback
     * @throws UPISDKException
     */
    private void initForRegistration(final InputAddDefaultVPA input,
                                     final APICallback<UPIPay> initCallback) throws UPISDKException {
        ProcessFlow.create(new UPIPay(this), initCallback)
                .addNext(new UserRegistrationSubflow(user, new ServiceCallback<Boolean>(Boolean.class) {
                    @Override
                    public void onSuccess(Boolean result) {
                        Log.d("App initialized:", String.valueOf(result));
                        initialized = result;
                    }

                    @Override
                    public void onError(WebServiceStatus status, List<Error> errors) {
                        if (errors != null && !errors.isEmpty()) {
                            initCallback.onFailure(errors.get(0).getErrorCode(), null);
                        } else {

                            initCallback.onFailure(SDKErrorCodes.ERR00097.name(), null);
                        }
                    }
                }))
                //.addNext(new SendOtpSubflow())
                .execute();

    }

    public String getUserId() {
        return user != null ? user.getUserId() : null;
    }


    public String getUserName() {
        return user != null ? user.getUserName() : null;
    }

    public String getUserMobile() {
        return user != null ? user.getUserMobile() : null;
    }

    public BigDecimal getUserMaxPayLinit() {
        return user != null ? user.getUserMaxPayLimit() : null;
    }

    public String getUserDefaultVpa() {
        return user != null ? user.getUserVpa() : null;
    }

    public String getaggrCode() {
        return user != null ? user.getAggrCode() : null;
    }

    public String getmrchCode() {
        return user != null ? user.getMrchCode() : null;
    }

    /**
     * UPI Pay class for exposing the API
     */
    public static final class UPIPay {

        private final UpiSDKContext context;

        private UPIPay(UpiSDKContext context) {
            this.context = context;
        }

        public String getUserId() {
            return this.context.getUserId();
        }


        public String getUserName() {
            return context.getUserName();
        }

        public String getUserMobile() {
            return context.getUserMobile();
        }

        public String getAggrCode() {
            return context.getaggrCode();
        }

        public String getMerchCode() {
            return context.getmrchCode();
        }

        public String getSessionToken() {
            return SDKUtils.getUsedK0();
        }

        public String getAccountStatus() {
            if (user.getUserStatus() != null)
                return user.getUserStatus();
            else
                return "";
        }

        public String getlastLoginTs() {
            if (user.getLastLoginTs() != null)
                return user.getLastLoginTs();
            else
                return "";
        }


        public BigDecimal getUserMaxPayLimit() {
            return context.getUserMaxPayLinit();
        }


        public void destroySDKInstance() {
            if (UpiSDKContext.getInstance().getCommonLibrary() != null
                    && UpiSDKContext.getInstance().getCommonLibrary().getClServices() != null) {
                CLServices clServices = commonLibrary.getClServices();
                clServices.unbindService();
                try {
                    Field clServieField = CLServices.class.getDeclaredField("clServices");
                    clServieField.setAccessible(true);
                    clServieField.set(clServices, null);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            initialized = false;
        }

        /**
         * Initializing the SDK
         */
        public static void initializeSDK(Context applicationContext, String customerRef, String passphrase,
                                         APICallback<UPIPay> initCallback)
                throws UPISDKException {
            UpiSDKContext.initialize(applicationContext, customerRef,
                    passphrase, initCallback);
        }

        /**
         * Managing Bank Accounts
         */

        public void addBankAccount(InputAddBankAccount input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new AddBankAccConverterSubflow())
                    .addNext(new BankAccAdditionSubflow())
                    .execute();
        }

        /**
         * Managing Bank Accounts
         */

        public void addBankAccountByMobile(InputAddBankAccount input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new AddBankAccConverterSubflow())
                    .addNext(new BankAccAdditionSubflowbyMobile())
                    .execute();
        }

        public void listBankAccount(InListLinkAccountQuery qry, final APICallback<LinkAccountDetails[]> callback)
                throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(qry, callback)
                    .addNext(new ListBankAccConverterSubflow())
                    .addNext(new BankAccListingSubflow())
                    .execute();
        }

        /**
         * List Bank Accounts by mobile
         */

        public void listBankAccountByMobileIfsc(InListLinkAccountQueryByMobile qry, final APICallback<LinkAccountDetails[]> callback)
                throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(qry, callback)
                    .addNext(new ListBankAccConverterSubflowByMobile())
                    .addNext(new BankAccListingSubflowByMobile())
                    .addNext(new BankAccListingSubflowByMobileFinal())
                    .execute();
        }

        /**
         * Fetch Banks
         *
         * @param callback
         * @throws UPISDKException
         */
        public void fetchBanks(final APICallback<PspBankList[]> callback)
                throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(null, callback)
                    .addNext(new FetchBankPspSubflow())
                    .execute();
        }


        public void deleteBankAccount(InputDeleteBankAccount input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new DeleteBankAccConverterSubflow())
                    .addNext(new BankAccDeletionSubflow())
                    .execute();
        }


        /**
         * Managing Virtual Payment Address
         */

        public static void checkVPAAvailability(InputcheckVPAAvailability input, final APICallback<Boolean> callback) throws UPISDKException {

            ProcessFlow.create(input, callback)
                    .addNext(new CheckVpaAvailabilityConverterSubflow())
                    .addNext(new CheckVpaAvailabilitySubflow())
                    .execute();
        }

        public void addVPA(InputAddVPA input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new AddVpaConverterSubflow())
                    .addNext(new VpaAdditionSubflow())
                    .execute();
        }

        public static void createDefaultVPA(Context context, InputAddDefaultVPA input, final APICallback<UPIPay> callback) throws UPISDKException {

            UpiSDKContext.initializeForRegistration(context, input, callback);
        }

        public void updateVPA(InputUpdateVPA input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new UpdateVpaConverterSubflow())
                    .addNext(new VpaUpdationSubflow())
                    .execute();

        }

        public void deleteVPA(InputDeleteVPA input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new DeleteVpaConverterSubflow())
                    .addNext(new VpaDeletionSubflow())
                    .execute();
        }


        public void listVPA(InListVPAQuery qry, final APICallback<Vpa[]> callback) throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(qry, callback)
                    .addNext(new ListVpaConverterSubflow())
                    .addNext(new VpaPopulatorSubflow())
                    .execute();
        }


        /**
         * Making a payment request
         */
        public void initiatePayment(InPayRequest payRequest, final APICallback<OutPayRequest> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            if (payRequest.getiStateListener() != null)
                payRequest.getiStateListener().getState(State.ACTIVE);

            ProcessFlow.create(payRequest, callback)
                    .addNext(new InPayReqConverterSubflow())
                    .addNext(new TransactionIdGeneratorSubflow())
                    .addNext(new CredentialCaptureSubflow())
                    .addNext(new PaymentInitiatorSubflow())
                    .execute();

        }

        /**
         * Making a collect request
         */
        public void initiateCollect(InPayRequest payRequest, final APICallback<OutPayRequest> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(payRequest, callback)
                    .addNext(new InPayReqConverterSubflow())
                    .addNext(new TransactionIdGeneratorSubflow())
                    .addNext(new PaymentInitiatorSubflow())
                    .execute();
        }

        /**
         * Transaction History Details
         */
        public void getTransactionHistory(InTxnHistoryQuery txnHistory, final APICallback<TransactionHistory[]> callback) throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(txnHistory, callback)
                    .addNext(new TransactionHistoryPopulatorSubflow())
                    .execute();
        }

        /**
         * Authorizing collection requests
         */
        public void authorizeCollectionRequest(InPaymentAuthorization authPaymentRequest, APICallback callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            if (authPaymentRequest.getiStateListener() != null)
                authPaymentRequest.getiStateListener().getState(State.ACTIVE);

            ProcessFlow.create(authPaymentRequest, callback)
                    .addNext(new PaymentAuthRequestConverterSubflow())
                    .addNext(new CredentialCaptureAuthSubflow())
                    .addNext(new PaymentAuthorizationSubflow())
                    .execute();
        }

        /**
         * Pending Collect Request
         */
        public void getPendingCollectRequests(InPendingTxnQuery pendingTxnQuery, final APICallback<OutPendingTxn[]> callback) throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(pendingTxnQuery, callback)
                    .addNext(new PendingTxnListPopulatorSubflow())
                    .execute();
        }

        public void sendOtpRequest(final APICallback<Boolean> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(null, callback)
                    .addNext(new SendOtpSubflow())
                    .execute();
        }

        public void bindDevice(String otp, final APICallback<Boolean> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(otp, new APICallback() {
                @Override
                public void onSuccess(Object result) {
                    callback.onSuccess(true);
                }

                @Override
                public void onFailure(String errorCode, String upiErrorCode) {
                    callback.onFailure(errorCode, upiErrorCode);
                }
            })
                    //  .addNext(new ValidateOtpSubflow(this.context.user))
                    .addNext(new RegisterAppSubflow(this.context.user, new ServiceCallback<Boolean>(Boolean.class) {
                        @Override
                        public void onSuccess(Boolean result) {
                            initialized = result;
                            // TODO Start Common Library initialization in the background

                            /// send success status to user login
                            callback.onSuccess(result);
                            user.setClToken("");
                        }

                        @Override
                        public void onError(WebServiceStatus status, List<Error> errors) {
                            if (errors != null && !errors.isEmpty()) {
                                callback.onFailure(errors.get(0).getErrorCode(), null);
                            } else {
                                callback.onFailure(SDKErrorCodes.ERR00097.name(), null);
                            }
                        }
                    }))
                    .addNext(new CLInitSubflow(user, new ServiceCallback<CommonLibrary>(CommonLibrary.class) {
                        @Override
                        public void onSuccess(CommonLibrary result) {
                            UpiSDKContext.commonLibrary = result;
                        }

                        @Override
                        public void onError(WebServiceStatus status, List<Error> errors) {
                            Log.d("UPI SDK Context", "Common Library has not been initialized");
                            UpiSDKContext.commonLibrary = null;
                        }
                    }, true))
                    .execute();
        }


        /**
         * Add Beneficiary
         */
        public void addBeneficiary(InputBeneficiary input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            if (input.getBeneficiaryType() != null && input.getBeneficiaryType().equals(BeneficiaryType.AADHAR)) {
                if (input.getAadhar_num() != null && input.getAadhar_num().length() > 0) {
                    input.setPaymentAddress(input.getAadhar_num() + "@aadhaar.npci");
                    input.setAadhar_num(null);
                }

            } else if (input.getBeneficiaryType() != null && input.getBeneficiaryType().equals(BeneficiaryType.ACCOUNT_IFSC)) {
                if (input.getAccount_no() != null && input.getAccount_no().length() > 0
                        && input.getIfsc() != null && input.getIfsc().length() > 0) {
                    String acc_ifsc = "";
                    acc_ifsc = input.getAccount_no() + "@" + input.getIfsc() + "." + "ifsc.npci";
                    input.setPaymentAddress(acc_ifsc);
                    input.setAccount_no(null);
                    input.setIfsc(null);
                }

            }

            input.setBeneficiaryType(null);

            ProcessFlow.create(input, callback)
                    .addNext(new AddBeneficiaryConverterSubflow())
                    .addNext(new AddBeneficiarySubflow())
                    .execute();
        }

        /**
         * Update Beneficiary
         */
        public void updateBeneficiary(InputUpdateBeneficiary input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new UpdateBeneficiaryConverterSubflow())
                    .addNext(new BeneficiaryUpdationSubflow())
                    .execute();

        }

        /**
         * Delete Beneficiary
         */
        public void deleteBeneficiary(InputDeleteBeneficiary input, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(input, callback)
                    .addNext(new DeleteBeneficiaryConverterSubflow())
                    .addNext(new BeneficiaryDeleteSubflow())
                    .execute();
        }

        /**
         * Get list of added beneficiary
         */
        public void listBeneficiary(InListAddedBeneficiaryQuery qry, final APICallback<BeneficiaryDetails[]> callback)
                throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(qry, callback)
                    .addNext(new ListBeneficiaryConverterSubflow())
                    .addNext(new BeneficiaryListingSubflow())
                    .execute();
        }

        /**
         * Get Account Balance
         */
        public void getAccountBalance(BalanceEnquiryRequest balanceEnquiryRequest,
                                      APICallback<BalanceEnquiryResponse> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            if (balanceEnquiryRequest.getiStateListener() != null)
                balanceEnquiryRequest.getiStateListener().getState(State.ACTIVE);

            ProcessFlow.create(balanceEnquiryRequest, callback)
                    .addNext(new BalanceEnquiryRequestConverterSubflow())
                    .addNext(new TransactionIdGeneratorSubflow())
                    .addNext(new BalanceEnquiryCredentialCaptureAuthSubflow())
                    .addNext(new BalanceEnquirySubflow(false))
                    .addNext(new BalanceEnquirySubflow(true))
                    .execute();
        }


        /**
         * Register Mobile Banking
         */
        public void registerMobileBanking(ReqRegMobile reqRegMobile,
                                          APICallback<RespRegMobile> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            if (reqRegMobile.getLinkAccountDetails() == null) {
                throw new UPISDKException(SDKErrorCodes.ERR00030);

            }

            if (reqRegMobile.getiStateListener() != null)
                reqRegMobile.getiStateListener().getState(State.ACTIVE);

            if (reqRegMobile.getAccNo() != null && reqRegMobile.getAccNo().length() > 0) {
                if (SDKUtils.LIST_ACCOUNT_MASKED != null && SDKUtils.LIST_ACCOUNT_MASKED.size() > 0) {
                    for (int i = 0; i < SDKUtils.LIST_ACCOUNT_MASKED.size(); i++) {
                        if (reqRegMobile.getAccNo().equalsIgnoreCase(SDKUtils.LIST_ACCOUNT_MASKED.get(i).getMaskedbankAcnumber())) {
                            reqRegMobile.setAccNo(SDKUtils.LIST_ACCOUNT_MASKED.get(i).getBankAcnumber());
                        }

                    }
                }
            }

            ProcessFlow.create(reqRegMobile, callback)
                    .addNext(new TransactionIdGeneratorSubflow())
                    .addNext(new RequestOtpSubflow())
                    .addNext(new RegMobCredentialCaptureAuthSubflow())
                    .addNext(new RegMobSubflow())
                    .addNext(new RegMobSubflowFinal())
                    .execute();
        }

        /**
         * Register Mobile Banking
         */
        public void forgetMpin(ForgetMpin forgetMpin,
                               APICallback<ResponseSetCre> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

//            reqRegMobile.set
            ReqRegMobile reqRegMobile = new ReqRegMobile();
            reqRegMobile.setBank_name(forgetMpin.getBank_name());
            reqRegMobile.setIfsc(forgetMpin.getIfsc());
            //reqRegMobile.setCredAllowed(forgetMpin.getCredAllowed());

            if (forgetMpin.getiStateListener() != null) {
                reqRegMobile.setiStateListener(forgetMpin.getiStateListener());
                reqRegMobile.getiStateListener().getState(State.ACTIVE);
            }

            ProcessFlow.create(reqRegMobile, callback)
                    .addNext(new TransactionIdGeneratorSubflow())
                    .addNext(new RequestOtpSubflow())
                    .addNext(new RegMobCredentialCaptureAuthSubflow())
                    .addNext(new ForgetMpinSubflow())
                    .addNext(new ForgetMpinSubflow())
                    .execute();
        }

        /**
         * Change MPIN
         */
        public void changeMPIN(InputChangeMPIN inputChangeMPIN, APICallback<ResponseSetCre> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            SetCredential setCredential = new SetCredential();
            setCredential.setBank_name(inputChangeMPIN.getBank_name());
            Payer payer = new Payer();
            payer.setIfsc(inputChangeMPIN.getIfsc());

            payer.setPayerUserId(UpiSDKContext.getInstance().getUserId());
            if (inputChangeMPIN.getCredAllowed() != null)
                setCredential.setCredAllowed(inputChangeMPIN.getCredAllowed());

            if (inputChangeMPIN.getiStateListener() != null) {
                setCredential.setiStateListener(inputChangeMPIN.getiStateListener());
                setCredential.getiStateListener().getState(State.ACTIVE);
            }

            if (inputChangeMPIN.getAccNo() != null && inputChangeMPIN.getAccNo().length() > 0) {
                if (SDKUtils.LIST_ACCOUNT_MASKED != null && SDKUtils.LIST_ACCOUNT_MASKED.size() > 0) {
                    for (int i = 0; i < SDKUtils.LIST_ACCOUNT_MASKED.size(); i++) {
                        if (inputChangeMPIN.getAccNo().equalsIgnoreCase(SDKUtils.LIST_ACCOUNT_MASKED.get(i).getMaskedbankAcnumber())) {
                            payer.setBankAcNumber(SDKUtils.LIST_ACCOUNT_MASKED.get(i).getBankAcnumber());
                        }

                    }
                }
            }

            payer.setBankAcType(inputChangeMPIN.getAccType());

            setCredential.setPayer(payer);
            if (inputChangeMPIN.getCredAllowed() != null)
                setCredential.setCredAllowed(inputChangeMPIN.getCredAllowed());

            ProcessFlow.create(setCredential, callback)
                    .addNext(new TransactionIdGeneratorSubflow())
                    .addNext(new ChangeMpinCredentialCaptureAuthSubflow())
                    .addNext(new ChangeMpinSubflow())
                    .addNext(new ChangeMpinSubflow())
                    .execute();
        }

        public void validateVPA(RequestValAddress requestValAddress, APICallback<ResponseValAddress> callback) throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            requestValAddress.setOwnerId(UpiSDKContext.getInstance().getUserId());
            ProcessFlow.create(requestValAddress, callback)
                    .addNext(new TransactionIdGeneratorSubflow())
                    .addNext(new ValidateVpaSubflow())
                    .addNext(new ValidateVpaSubflowFinal())
                    .execute();

        }

        public void clearAccountDetails() {
            SDKUtils.LIST_ACCOUNT_MASKED = new ArrayList<BankAccountBean>();
        }

        public void deregisterProfile(String userId, APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            ProcessFlow.create(userInfo, callback)
                    .addNext(new DeRegisterUserSubflow(user))
                    .execute();

        }

        public void raiseDispute(RaiseDispute raiseDispute, APICallback<RaiseDispute> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            ProcessFlow.create(raiseDispute, callback)
                    .addNext(new RaiseDisputeSubflow())
                    .execute();

        }

        public void transactionHistoryetails(InputTxnHistoryDetails transactionHitoryDetails, APICallback<TxnHistoryDetails> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            TxnHistoryDetails txnHistoryDetails = new TxnHistoryDetails();
            txnHistoryDetails.setOwnerId(transactionHitoryDetails.getOwnerId());
            txnHistoryDetails.setTxnId(transactionHitoryDetails.getTxnId());
            if (transactionHitoryDetails.getRefId() != null && transactionHitoryDetails.getRefId().length() > 0)
                txnHistoryDetails.setRefId(transactionHitoryDetails.getRefId());

            ProcessFlow.create(txnHistoryDetails, callback)
                    .addNext(new TransactionHistoryDetailsSubflow())
                    .execute();

        }


        public void changePassword(ChangePassword changePassword, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            String old_pass = "";
            String new_pass = "";
            if (changePassword.getUserPassword() != null && changePassword.getUserPassword().length() > 0
                    && changePassword.getNewPassword() != null && changePassword.getNewPassword().length() > 0) {
                old_pass = SDKUtils.getEncryptedPassword(changePassword.getUserPassword());
                new_pass = SDKUtils.getEncryptedPassword(changePassword.getNewPassword());
                changePassword.setUserPassword(old_pass);
                changePassword.setNewPassword(new_pass);

            }
            ProcessFlow.create(changePassword, callback)
                    .addNext(new ChangePasswordSubflow())
                    .execute();
        }

        public void getTransactionDetailsByRefId(String txnRefId, Mode mode, APICallback<TxnHistoryDetails> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            TxnHistoryDetails txnHistoryDetails = new TxnHistoryDetails();
            txnHistoryDetails.setOwnerId(UpiSDKContext.getInstance().getUserId());
            txnHistoryDetails.setTxnRefId(txnRefId);
            txnHistoryDetails.setMode(mode);
            ProcessFlow.create(txnHistoryDetails, callback)
                    .addNext(new TransactionHistoryDetailsSubflowByRefId())
                    .execute();

        }

        public void activateVPA(InputActivateVPA inputActivateVPA, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            ProcessFlow.create(inputActivateVPA, callback)
                    .addNext(new ActivateVPASubflow())
                    .execute();
        }

        public void deActivateVPA(InputActivateVPA inputActivateVPA, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            ProcessFlow.create(inputActivateVPA, callback)
                    .addNext(new DeActivateVPASubflow())
                    .execute();
        }

        public void listNotificationMessages(InputNotification inputNotification, final APICallback<Notifications[]> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            ProcessFlow.create(inputNotification, callback)
                    .addNext(new NotificationsPopulatorSubflow())
                    .execute();
        }

        public void addSchedule(InputSchedule inputSchedule, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            ProcessFlow.create(inputSchedule, callback)
                    .addNext(new AddSchedulePopulatorSubflow())
                    .execute();
        }

        public void deleteSchedule(InputDeleteSchedule inputSchedule, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            ProcessFlow.create(inputSchedule, callback)
                    .addNext(new DeleteSchedulePopulatorSubflow())
                    .execute();
        }

        public void listSchedule(String userId, final APICallback<Schedule[]> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            InputDeleteSchedule inputDeleteSchedule = new InputDeleteSchedule();
            if (userId != null && userId.length() > 0)
                inputDeleteSchedule.setOwnerId(userId);
            else
                throw new UPISDKException(SDKErrorCodes.ERR00024);

            ProcessFlow.create(inputDeleteSchedule, callback)
                    .addNext(new ListSchedulePopulatorSubflow())
                    .execute();
        }

        public void updateSchedule(Schedule inputSchedule, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            ProcessFlow.create(inputSchedule, callback)
                    .addNext(new UpdateSchedulePopulatorSubflow())
                    .execute();
        }

        //RBL requirements...
        public void setApiKey(String apiKey) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }

            InputChannelId inputChannelId = new InputChannelId();
            if (apiKey != null && apiKey.length() > 0)
                inputChannelId.setApiKey(apiKey);
            inputChannelId.setAppId(UpiSDKContext.getInstance().getApplicationContext().getPackageName());
            ProcessFlow.create(inputChannelId, new APICallback() {
                @Override
                public void onSuccess(Object result) {
                }

                @Override
                public void onFailure(String SdkErrorCode, String UpiErrorCode) {
                }
            })
                    .addNext(new ChannelIdPopulatorSubflow())
                    .execute();
        }

        /**
         * Transaction History By cust ref Id
         */
        public void getTransactionHistoryByCustRefId(String custRefId, final APICallback<TransactionHistory[]> callback) throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            if (custRefId != null && custRefId.length() > 0) {
                ListParm param = new ListParm();
                param.setParmType(UserEntityType.TRAN_HIST);
                param.setUserId(UpiSDKContext.getInstance().getUserId());
                param.setCustRefId(custRefId);
                param.setMode("ALL");
                ProcessFlow.create(param, callback)
                        .addNext(new TransactionHistoryPopulatorSubflowByRefId())
                        .execute();
            } else {
                throw new UPISDKException(SDKErrorCodes.ERR00098);
            }


        }

        /**
         * Verify VAE Merchant
         */
        public void verifyVAE(String vaeAddr, final APICallback<VaeDetails> callback) throws UPISDKException {

            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            if (vaeAddr != null && vaeAddr.length() > 0) {
                VaeDetails param = new VaeDetails();
                param.setVaeAddr(vaeAddr);
                ProcessFlow.create(param, callback)
                        .addNext(new VaeDetailsSubflow())
                        .execute();
            } else {
                throw new UPISDKException(SDKErrorCodes.ERR00098);
            }


        }

//        /**
//         * Transaction History By VPA
//         */
//        public void getTransactionHistoryByVPA(InTxnHistoryQuery txnHistory, final APICallback<TransactionHistory[]> callback) throws UPISDKException {
//
//            if (!UpiSDKContext.isInitialized()) {
//                throw new UPISDKException(SDKErrorCodes.ERR00103);
//            }
//
//            ProcessFlow.create(txnHistory, callback)
//                    .addNext(new TransactionHistoryPopulatorSubflowByVPA())
//                    .execute();
//        }

        /**
         * Add Update User Limit
         */
        public void addUpdateUserLimit(InputAddUpdateUserLimit inputAddUpdateUserLimit, final APICallback<String> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            ProcessFlow.create(inputAddUpdateUserLimit, callback)
                    .addNext(new AddUpdateUserLimitConverterSubflow())
                    .addNext(new AddUpdateUserLimitPopulatorSubflow())
                    .execute();
        }


        /**
         * Calculate Service Charge
         */
        public void getServiceCharge(InputServiceCharge inputServiceCharge, final APICallback<ServiceChargeResult> callback) throws UPISDKException {
            if (!UpiSDKContext.isInitialized()) {
                throw new UPISDKException(SDKErrorCodes.ERR00103);
            }
            if (inputServiceCharge.getPayeeVpa() != null && inputServiceCharge.getPayerVpa() != null
                    && inputServiceCharge.getTxnAmount() != null && inputServiceCharge.getTxnType().name() != null) {
                ProcessFlow.create(inputServiceCharge, callback)
                        .addNext(new CalculateServiceChargeSubflow())
                        .execute();
            } else {
                callback.onFailure(SDKErrorCodes.ERR00098.name(),null);
            }
        }
    }


}
