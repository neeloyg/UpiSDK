package com.upi.sdk.services;

import android.content.Context;
import android.util.Log;

import com.rssoftware.upiint.schema.AddUpdateUserLimit;
import com.rssoftware.upiint.schema.BalEnquiryResp;
import com.rssoftware.upiint.schema.BalanceEnquiry;
import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.rssoftware.upiint.schema.ConfigElement;
import com.rssoftware.upiint.schema.DeviceDetails;
import com.rssoftware.upiint.schema.LinkAccount;
import com.rssoftware.upiint.schema.LinkAccountDetails;
import com.rssoftware.upiint.schema.ListKeysResponse;
import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.Notifications;
import com.rssoftware.upiint.schema.OtpServiceDetails;
import com.rssoftware.upiint.schema.PaymentAuthRequest;
import com.rssoftware.upiint.schema.PaymentRequest;
import com.rssoftware.upiint.schema.PspBankList;
import com.rssoftware.upiint.schema.RaiseDispute;
import com.rssoftware.upiint.schema.RegisterApp;
import com.rssoftware.upiint.schema.ReqRegMobile;
import com.rssoftware.upiint.schema.RequestOtp;
import com.rssoftware.upiint.schema.RequestValAddress;
import com.rssoftware.upiint.schema.RespRegMobile;
import com.rssoftware.upiint.schema.ResponseSetCre;
import com.rssoftware.upiint.schema.ResponseValAddress;
import com.rssoftware.upiint.schema.ServiceRequest;
import com.rssoftware.upiint.schema.ServiceResponse;
import com.rssoftware.upiint.schema.SetCredential;
import com.rssoftware.upiint.schema.TokenRequest;
import com.rssoftware.upiint.schema.TokenResponse;
import com.rssoftware.upiint.schema.TransactionHistory;
import com.rssoftware.upiint.schema.InputTxnHistoryDetails;
import com.rssoftware.upiint.schema.TxnHistoryDetails;
import com.rssoftware.upiint.schema.User;
import com.rssoftware.upiint.schema.UserValidation;
import com.rssoftware.upiint.schema.UserView;
import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.domain.ChangePassword;
import com.upi.sdk.domain.InputActivateVPA;
import com.upi.sdk.domain.InputChannelId;
import com.upi.sdk.domain.InputDeleteSchedule;
import com.upi.sdk.domain.InputNotification;
import com.upi.sdk.domain.InputSchedule;
import com.upi.sdk.domain.InputServiceCharge;
import com.upi.sdk.domain.OutPendingTxn;
import com.upi.sdk.domain.Schedule;
import com.upi.sdk.domain.ServiceChargeResult;
import com.upi.sdk.domain.UpdatePassword;
import com.upi.sdk.domain.UserInfo;
import com.upi.sdk.domain.VaeDetails;
import com.upi.sdk.utils.SDKUtils;

import retrofit2.Call;

/**
 * Created by SwapanP on 22-04-2016.
 */
public class UppServices {

    /**
     * List all transactions for an user id
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getAllTransactions(ListParm param, ServiceCallback<TransactionHistory[]> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.getAllTransactions(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * List all pending collect Request for an user id
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getAllCollectRequest(ListParm param, ServiceCallback<OutPendingTxn[]> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.getAllPendingTransaction(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * Get transaction Id for next transaction
     *
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getTransactionId(ServiceCallback<String> serviceCallBack) {

        ServiceExecutor.execute(null, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.getTransactionId(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * Initiates a Payment based on the Payment Request bean supplied
     *
     * @param paymentRequest  Payment Request bean containing the information related to payments
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void initiatePayment(final PaymentRequest paymentRequest, ServiceCallback<String> serviceCallBack) {

        ServiceExecutor.execute(paymentRequest, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.initiatePayment(request);
                    }
                },
                serviceCallBack,
                new DeviceDetailsPopulator() {
                    @Override
                    public DeviceDetails populate() {
                        return SDKUtils.getDeviceDetails(HmacGenerationPolicy.PAYMENT, paymentRequest);
                    }
                });
    }

    /**
     * Initiates a collect request based on the Payment Request bean supplied
     *
     * @param paymentRequest
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void initiateCollect(final PaymentRequest paymentRequest, ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(paymentRequest, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.initiateCollect(request);
                    }
                },
                serviceCallBack,
                new DeviceDetailsPopulator() {
                    @Override
                    public DeviceDetails populate() {
                        return SDKUtils.getDeviceDetails(HmacGenerationPolicy.PAYMENT, paymentRequest);
                    }
                });
    }

    /**
     * Authorizes a collect request
     *
     * @param paymentAuthRequest Bean containing the authorization details for the current user and account
     * @param serviceCallBack    Call back method for success and failure status
     */
    public static void authorizePaymentRequest(PaymentAuthRequest paymentAuthRequest,
                                               ServiceCallback<Void> serviceCallBack) {

        ServiceExecutor.execute(paymentAuthRequest, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.authorizeTransaction(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Adding bank account for an user id
     *
     * @param @LinkAccount    Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */

    public static void AddBankAccount(LinkAccount linkAccount, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(linkAccount, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.addBankAccount(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Adding bank account for an user id
     *
     * @param @LinkAccount    Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */

    public static void AddBankAccountByMobile(LinkAccount linkAccount, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(linkAccount, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.addBankAccountByMobile(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List of  bank accounts for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void listBankAccounts(ListParm param, final ServiceCallback<LinkAccountDetails[]> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.listBankAccounts(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List of  bank accounts by mobile for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */

    public static void listBankAccountsByMobile(ListParm param, final ServiceCallback<ListParm> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.listBankAccountsByMobile(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List of  bank accounts by mobile for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */

    public static void listBankAccountsByMobileFinal(ListParm param, final ServiceCallback<LinkAccountDetails[]> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.listBankAccountsByMobile(request);
                    }
                },
                serviceCallBack);
    }


    /**
     * This method fetches banks which are registered in UPI network, from the bank end
     *
     * @param param
     * @param serviceCallback
     */
    public static void fetchBankPSPs(final ListParm param, final ServiceCallback<PspBankList[]> serviceCallback) {

        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.fetchBanks(request);
                    }
                },
                serviceCallback);
    }

    /**
     * Delete bank account for a user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void DeleteBankAccount(LinkAccount linkAccount, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(linkAccount, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.deleteBankAccount(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Check available VPA for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void checkVpaAvailability(UserValidation userValParm, final ServiceCallback<Boolean> serviceCallBack) {
        ServiceExecutor.checkVpaAvailable(userValParm, serviceCallBack);
    }

    /**
     * List of  all vpa's for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void listVPAs(ListParm param, final ServiceCallback<Vpa[]> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.listVPA(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Add vpa for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void AddVPA(Vpa vpa, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(vpa, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.addVPA(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Update vpa for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void UpdateVPA(Vpa vpa, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(vpa, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.updateVPA(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Delete vpa for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void DeleteVPA(Vpa vpa, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(vpa, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.deleteVPA(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Get Token for Common Library Initialization
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getToken(TokenRequest param, final ServiceCallback<TokenResponse> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(RegisterAppServices service,
                                                                 ServiceRequest request) {
                        return service.initiateToken(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Get ListKeys Common Library Initialization
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getListKeys(TokenRequest param, final ServiceCallback<ListKeysResponse> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(RegisterAppServices service,
                                                                 ServiceRequest request) {
                        return service.initiateListKeys(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Registering app for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void registerApp(RegisterApp param, final ServiceCallback<String> serviceCallBack) {

        ServiceExecutor.registerApplication(param, serviceCallBack);
    }

    public static void login(final User user, ServiceCallback<UserView> serviceCallback) {
        //ServiceExecutor.login(user, serviceCallback);
        Log.d("Login Service", "Starting login service");
        ServiceExecutor.execute(user, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {
            @Override
            public Call<ServiceResponse> identifyService(RegisterAppServices service, ServiceRequest request) {
                Log.d("Login Service", "Invoking service call");
                return service.userLogin(request);
            }
        }, serviceCallback);
    }

    public static void registerUser(final User user, ServiceCallback<UserView> serviceCallback) {
        Log.d("Register User Service", "Starting login service");
        ServiceExecutor.registerUser(user, serviceCallback);
    }

    public static void sendOtpRequest(OtpServiceDetails request, ServiceCallback<Void> callback) {
        Log.d("OTP Service", "Send Otp Service called");
        ServiceExecutor.execute(request, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {
            @Override
            public Call<ServiceResponse> identifyService(RegisterAppServices service, ServiceRequest request) {
                return service.sendOtp(request);
            }
        }, callback);
    }

    public static void requestOTP(RequestOtp request, ServiceCallback<Void> callback) {
        Log.d("OTP Service", "Request Otp Service called");
        ServiceExecutor.execute(request, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {
            @Override
            public Call<ServiceResponse> identifyService(RegisterAppServices service, ServiceRequest request) {
                return service.requestOTP(request);
            }
        }, callback);
    }

    public static void validateOtp(User request, ServiceCallback<UserView> callback) {
        Log.d("OTP Service", "Validate Otp Service called");
        ServiceExecutor.execute(request, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {
            @Override
            public Call<ServiceResponse> identifyService(RegisterAppServices service, ServiceRequest request) {
                return service.validateOtp(request);
            }
        }, callback);
    }

    /**
     * Add beneficiary for a user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void addBeneficiary(BeneficiaryDetails beneficiaryDetails, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(beneficiaryDetails, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.addBeneficiary(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Update beneficiary for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void updateBeneficiary(BeneficiaryDetails beneficiaryDetails, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(beneficiaryDetails, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.updateBeneficiary(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Delete beneficiary for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void deleteBeneficiary(BeneficiaryDetails beneficiaryDetails, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(beneficiaryDetails, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.deleteBeneficiary(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List of  Beneficiary for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void listBeneficiary(BeneficiaryDetails param, final ServiceCallback<BeneficiaryDetails[]> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.listBeneficiary(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List of  Beneficiary for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void fetchConfiguration(ConfigElement param, final ServiceCallback<ConfigElement> serviceCallBack) {
        ServiceExecutor.fetchConfiguration(param, serviceCallBack);
    }

    /**
     * Get Balance for an account
     *
     * @param balanceEnquiry  Bean containing the details of account and user
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getAccountBalance(BalanceEnquiry balanceEnquiry,
                                         ServiceCallback<BalEnquiryResp> serviceCallBack) {

        ServiceExecutor.execute(balanceEnquiry, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.getAccountBalance(request);
                    }
                },
                serviceCallBack);
    }


    /**
     * Register Mobile Banking
     *
     * @param reqRegMobile    Bean containing the details of account and user
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void registerMobileBanking(ReqRegMobile reqRegMobile,
                                             ServiceCallback<RespRegMobile> serviceCallBack) {

        ServiceExecutor.execute(reqRegMobile, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.registerMobileBanking(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Change MPIN
     *
     * @param setCredential   Bean containing the details of account and user
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void changeMPIN(SetCredential setCredential,
                                  ServiceCallback<ResponseSetCre> serviceCallBack) {

        ServiceExecutor.execute(setCredential, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.changeMPIN(request);
                    }
                },
                serviceCallBack);
    }


    /**
     * Check VPA Validity
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */

    public static void validateVPA(RequestValAddress param, final ServiceCallback<ResponseValAddress> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.validateVPA(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Get transaction Id for next transaction
     *
     * @param serviceCallBack Call back method for success and failure status
     */

    /**
     * List of  Beneficiary for an user
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void validateMobileNumber(final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.validateMobile(serviceCallBack);
    }

    /**
     * Send OTP for password Reset
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
//    public static void sendOTPforPasswordReset(UserInfo userInfo, final ServiceCallback<String> serviceCallBack) {
//        ServiceExecutor.sendOTPforPasswordReset(userInfo, serviceCallBack);
//    }
//Needed for RBL..............
    public static void sendOTPforPasswordReset(final UserInfo userInfo, final ServiceCallback<String> serviceCallBack) {
        final Context context=userInfo.getContext();
        userInfo.setContext(null);
        if(context!=null){
            ServiceExecutor.executeBeforeSDKsetUp(userInfo, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {
                        @Override
                        public Call<ServiceResponse> identifyService(RegisterAppServices service,
                                                                     ServiceRequest request) {
                            return service.sendOtpForPwdReset(request);
                        }
                    },
                    serviceCallBack,
                    new DeviceDetailsPopulator() {
                        @Override
                        public DeviceDetails populate() {
                            return SDKUtils.getDeviceDetailsForgetPasswordFlow(context, HmacGenerationPolicy.FORGET_PASSWORD,
                                    userInfo.getUserId(), "");
                        }
                    },context);
        }else{
            ServiceExecutor.sendOTPforPasswordReset(userInfo, serviceCallBack);
        }
    }

    /**
     * Validate OTP for password Reset
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void validateOTPforPasswordReset(OtpServiceDetails userInfo, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.validateOTPforPasswordReset(userInfo, serviceCallBack);
    }

    /**
     * Update PIN
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void updatePIN(final UpdatePassword userInfo, final ServiceCallback<String> serviceCallBack) {
        final Context context=userInfo.getContext();
        userInfo.setContext(null);
        if(context!=null){
            ServiceExecutor.executeBeforeSDKsetUp(userInfo, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {

                        @Override
                        public Call<ServiceResponse> identifyService(RegisterAppServices service,
                                                                     ServiceRequest request) {
                            return service.updatePassword(request);
                        }
                    },
                    serviceCallBack,
                    new DeviceDetailsPopulator() {
                        @Override
                        public DeviceDetails populate() {
                            return SDKUtils.getDeviceDetailsForgetPasswordFlow(context, HmacGenerationPolicy.FORGET_PASSWORD,
                                    userInfo.getUserId(), "");
                        }
                    },context);
        }else{
            ServiceExecutor.updatePIN(userInfo, serviceCallBack);
        }

    }

    /**
     * Delete user profile
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void deregisterProfile(UserInfo userInfo, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(userInfo, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(RegisterAppServices service,
                                                                 ServiceRequest request) {
                        return service.deregisterProfile(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Raise Dispute
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */

    public static void raiseDispute(RaiseDispute param, final ServiceCallback<RaiseDispute> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.raiseDispute(request);
                    }
                },
                serviceCallBack);
    }

    public static void transactionHistoryDetails(TxnHistoryDetails param, final ServiceCallback<TxnHistoryDetails> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.transactionHistoryDetails(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Change profile password
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void changePassword(ChangePassword userInfo, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(userInfo, new ServiceIdentifier<RegisterAppServices>(RegisterAppServices.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(RegisterAppServices service,
                                                                 ServiceRequest request) {
                        return service.changeProfilePassword(request);
                    }
                },
                serviceCallBack);
    }

    public static void transactionHistoryDetailsByRefId(TxnHistoryDetails param, final ServiceCallback<TxnHistoryDetails> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.transactionHistoryDetailsByRefId(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * Activate VPA
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void activateVPA(InputActivateVPA userInfo, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(userInfo, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.activateVPA(request);
                    }
                },
                serviceCallBack);
    }
    /**
     * De-Activate VPA
     *
     * @param @param          Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void deActivateVPA(InputActivateVPA userInfo, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(userInfo, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.deActivateVPA(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List all notifications
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getAllNotifications(InputNotification param, ServiceCallback<Notifications[]> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.getAllNotifications(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * Add schedule
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void addSchedule(InputSchedule param, ServiceCallback<Schedule> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.addSchedule(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * Delete schedule
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void deleteSchedule(InputDeleteSchedule param, ServiceCallback<Schedule> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.removeSchedule(request);
                    }
                },
                serviceCallBack);

    }


    /**
     * list schedule
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void listSchedule(InputDeleteSchedule param, ServiceCallback<Schedule[]> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.listSchedule(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * Update schedule
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void updateSchedule(Schedule param, ServiceCallback<Schedule> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.updateSchedule(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * Set ChannelId
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void fetchChannelId(InputChannelId param, final ServiceCallback<String> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.fetchChannelId(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List all transactions for an user by date filter
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getAllTransactionsByDateFilter(ListParm param, ServiceCallback<TransactionHistory[]> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.getAllTransactionsByDateFilter(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * List all transactions for an user id
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getAllTransactionsByCustRefId(ListParm param, ServiceCallback<TransactionHistory[]> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.getAllTransactionsByCustRefId(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * VAE Details of a merchant
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getVaeDetails(VaeDetails param, final ServiceCallback<VaeDetails> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.isVerifiedVae(request);
                    }
                },
                serviceCallBack);
    }

    /**
     * List all transactions for an user id
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void getAllTransactionsByVPA(ListParm param, ServiceCallback<TransactionHistory[]> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.getAllTransactionsByVPA(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * Update schedule
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void addUpdateUserLimit(AddUpdateUserLimit param, ServiceCallback<String> serviceCallBack) {

        ServiceExecutor.execute(param, new ServiceIdentifier<ManagementService>(ManagementService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(ManagementService service,
                                                                 ServiceRequest request) {
                        return service.addOrUpdateUserlimit(request);
                    }
                },
                serviceCallBack);

    }

    /**
     * VAE Details of a merchant
     *
     * @param param           Param class containing the filters
     * @param serviceCallBack Call back method for success and failure status
     */
    public static void calculateServiceCharge(InputServiceCharge param, final ServiceCallback<ServiceChargeResult> serviceCallBack) {
        ServiceExecutor.execute(param, new ServiceIdentifier<TransactionService>(TransactionService.class) {

                    @Override
                    public Call<ServiceResponse> identifyService(TransactionService service,
                                                                 ServiceRequest request) {
                        return service.calculateServiceCharge(request);
                    }
                },
                serviceCallBack);
    }


}
