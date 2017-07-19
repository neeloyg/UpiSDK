package com.upi.sdk.services;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rssoftware.upiint.schema.ConfigElement;
import com.rssoftware.upiint.schema.DeviceDetails;
import com.rssoftware.upiint.schema.OtpServiceDetails;
import com.rssoftware.upiint.schema.RegisterApp;
import com.rssoftware.upiint.schema.ServiceRequest;
import com.rssoftware.upiint.schema.ServiceResponse;
import com.rssoftware.upiint.schema.User;
import com.rssoftware.upiint.schema.UserValidation;
import com.rssoftware.upiint.schema.UserView;
import com.upi.sdk.CryptLib;
import com.upi.sdk.core.UPPSDKConstants;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.UpdatePassword;
import com.upi.sdk.domain.UserInfo;
import com.upi.sdk.utils.JsonUtils;
import com.upi.sdk.utils.SDKUtils;

import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class ServiceExecutor {

    private static Map<String, Object> serviceCallMap = new HashMap<>();
    private static Retrofit retrofitInstance = null;
    private static Certificate ca;

    static {

        Gson gson = new GsonBuilder().setLenient().create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        retrofitInstance = new Retrofit.Builder()
                .baseUrl(UPPSDKConstants.UPP_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        serviceCallMap.put(TransactionService.class.getName(), retrofitInstance.create(TransactionService.class));
        serviceCallMap.put(RegisterAppServices.class.getName(), retrofitInstance.create(RegisterAppServices.class));
        serviceCallMap.put(ManagementService.class.getName(), retrofitInstance.create(ManagementService.class));
    }


    public static <I, O, T> void execute(I in, ServiceIdentifier<T> identifier,
                                         final ServiceCallback<O> serviceCallback) {
        execute(in, identifier, serviceCallback, new DeviceDetailsPopulator() {
            @Override
            public DeviceDetails populate() {
                return SDKUtils.getDeviceDetails(HmacGenerationPolicy.GENERAL);
            }
        });
    }

    public static <I, O, T> void execute(I in, ServiceIdentifier<T> identifier,
                                         final ServiceCallback<O> serviceCallback,
                                         final DeviceDetailsPopulator deviceDtlsPopulator) {
        Log.d("Generic Service", "Executing service");

            ServiceRequest request = new ServiceRequest();

            /// Populate Device Details
            if (deviceDtlsPopulator != null) {
                DeviceDetails deviceDetails = deviceDtlsPopulator.populate();
                if (deviceDetails != null) {
                    request.setDeviceDetails(deviceDetails);
                }
            }


            request.setRequest(in);

//        //Set encrypted data.....
//
//        String json = JsonUtils.gson.toJson(request);
//        CryptLib cryptLib = new CryptLib();
//        String message="";
//        try {
//            byte[] calculatedHash = json.getBytes("UTF-8");
//            byte[] encryptedData = cryptLib.encrypt(calculatedHash,
//                    cryptLib.hexStringToByteArray(SDKUtils.K0HEX));
//            message = Base64.encodeToString(encryptedData, Base64.NO_WRAP);
//
//            Log.e("data to base64 request", message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

            // Log.e("input_request",request.toString());
            T service = (T) serviceCallMap.get(identifier.getTypeClass().getName());
            Log.d("Generic Service", "Executing service object: " + service.getClass().getName());
//        RequestBody body =
//                RequestBody.create(MediaType.parse("text/plain"), message);
            Call<ServiceResponse> serviceCall = identifier.identifyService(service, request);
            if (SDKUtils.isNetworkAvailable(UpiSDKContext.getInstance().getApplicationContext())) {
                serviceCall.enqueue(new Callback<ServiceResponse>() {
                    @Override
                    public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                        Log.d("Generic Service", "Received service response");
                        if (response != null) {
                            ServiceResponse serviceResponse = response.body();
                            if (serviceResponse != null) {

                                if (TextUtils.isEmpty(serviceResponse.getResponseCode())) {
                                    serviceCallback.onError(WebServiceStatus.FAILURE,
                                            new ArrayList<com.rssoftware.upiint.schema.Error>());
                                }

                                if (serviceResponse.getResponseCode() != null) {
                                    if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                                        Log.d("Generic Service", "Received service response with success");
                                        try {
                                            if (!Void.class.equals(serviceCallback.getTypeClass())) {
                                                O output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                                                serviceCallback.onSuccess(output);
                                            } else {
                                                serviceCallback.onSuccess(null);
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            serviceCallback.onError(WebServiceStatus.FAILURE, null);
                                        }
                                    } else {
                                        Log.d("Generic Service", "Received null service response");
                                        serviceCallback.onError(WebServiceStatus.FAILURE, serviceResponse.getError());
                                    }
                                } else {
                                    serviceCallback.onError(WebServiceStatus.FAILURE, serviceResponse.getError());
                                }
                            } else {
                                Log.d("Generic Service", "Received null response");
                                serviceCallback.onError(WebServiceStatus.FAILURE, null);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ServiceResponse> call, Throwable t) {
                        serviceCallback.onError(WebServiceStatus.FAILURE, null);

                    }
                });
            } else {
                Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
            }



    }

    public static <I, O, T> void executeBeforeSDKsetUp(I in, ServiceIdentifier<T> identifier,
                                                       final ServiceCallback<O> serviceCallback,
                                                       final DeviceDetailsPopulator deviceDtlsPopulator, Context context) {
        Log.d("Generic Service", "Executing service");
        ServiceRequest request = new ServiceRequest();

        /// Populate Device Details
        if (deviceDtlsPopulator != null) {
            DeviceDetails deviceDetails = deviceDtlsPopulator.populate();
            if (deviceDetails != null) {
                request.setDeviceDetails(deviceDetails);
            }
        }
        request.setRequest(in);
        T service = (T) serviceCallMap.get(identifier.getTypeClass().getName());
        Log.d("Generic Service", "Executing service object: " + service.getClass().getName());
        Call<ServiceResponse> serviceCall = identifier.identifyService(service, request);
        if (SDKUtils.isNetworkAvailable(context)) {
            serviceCall.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                    Log.d("Generic Service", "Received service response");
                    if (response != null) {
                        ServiceResponse serviceResponse = response.body();
                        if (serviceResponse != null) {

                            if (TextUtils.isEmpty(serviceResponse.getResponseCode())) {
                                serviceCallback.onError(WebServiceStatus.FAILURE,
                                        new ArrayList<com.rssoftware.upiint.schema.Error>());
                            }

                            if (serviceResponse.getResponseCode() != null) {
                                if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                                    Log.d("Generic Service", "Received service response with success");
                                    try {
                                        if (!Void.class.equals(serviceCallback.getTypeClass())) {
                                            O output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                                            serviceCallback.onSuccess(output);
                                        } else {
                                            serviceCallback.onSuccess(null);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        serviceCallback.onError(WebServiceStatus.FAILURE, null);
                                    }
                                } else {
                                    Log.d("Generic Service", "Received null service response");
                                    serviceCallback.onError(WebServiceStatus.FAILURE, serviceResponse.getError());
                                }
                            } else {
                                serviceCallback.onError(WebServiceStatus.FAILURE, serviceResponse.getError());
                            }
                        } else {
                            Log.d("Generic Service", "Received null response");
                            serviceCallback.onError(WebServiceStatus.FAILURE, null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {
                    serviceCallback.onError(WebServiceStatus.FAILURE, null);
                }
            });
        } else {
            Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
        }
    }

    public static void registerApplication(RegisterApp registerApp, final ServiceCallback<String> serviceCallback) {
        RegisterAppServices service = (RegisterAppServices) serviceCallMap.get(RegisterAppServices.class.getName());
        Call<ServiceResponse> serviceCall = service.registerApplication(registerApp);
        if (SDKUtils.isNetworkAvailable(UpiSDKContext.getInstance().getApplicationContext())) {
            serviceCall.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                    if (response != null) {
                        ServiceResponse serviceResponse = response.body();
                        if (serviceResponse != null) {
                            if (serviceResponse.getResponseCode() != null) {
                                if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                                    serviceCallback.onSuccess((String) serviceResponse.getResult());
                                } else {
                                    serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                                }
                            } else {
                                Log.d("Generic Service", "Received null service response");
                                serviceCallback.onError(WebServiceStatus.FAILURE, null);
                            }
                        } else {
                            Log.d("Generic Service", "Received null response");
                            serviceCallback.onError(WebServiceStatus.FAILURE, null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {
                    serviceCallback.onError(WebServiceStatus.FAILURE, null);
                }

            });

        } else {
            Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(), "Please check your network connection", Toast.LENGTH_SHORT).show();
        }

    }

    public static void registerUser(User user, final ServiceCallback<UserView> serviceCallback) {
        RegisterAppServices service = (RegisterAppServices) serviceCallMap.get(RegisterAppServices.class.getName());
        ServiceRequest request = new ServiceRequest();
        request.setDeviceDetails(SDKUtils.getDeviceDetails(HmacGenerationPolicy.GENERAL));
        request.setRequest(user);
        Call<ServiceResponse> serviceCall = service.registerUser(request);
        serviceCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response != null) {
                    ServiceResponse serviceResponse = response.body();
                    if (serviceResponse != null) {
                        if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                            UserView output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                            serviceCallback.onSuccess(output);
                        } else {
                            serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                serviceCallback.onError(WebServiceStatus.FAILURE, null);
            }
        });
    }

    public static void checkVpaAvailable(UserValidation userValidation, final ServiceCallback<Boolean> serviceCallback) {
        ManagementService service = (ManagementService) serviceCallMap.get(ManagementService.class.getName());
        ServiceRequest request = new ServiceRequest();
        request.setRequest(userValidation);
        Call<ServiceResponse> serviceCall = service.checkVPAAvailability(request);
        serviceCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response != null) {
                    ServiceResponse serviceResponse = response.body();
                    if (serviceResponse != null) {
                        if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                            serviceCallback.onSuccess((Boolean) serviceResponse.getResult());
                        } else {
                            serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                serviceCallback.onError(WebServiceStatus.FAILURE, null);
            }
        });
    }

    public static void fetchConfiguration(ConfigElement configElement, final ServiceCallback<ConfigElement> serviceCallback) {
        RegisterAppServices service = (RegisterAppServices) serviceCallMap.get(RegisterAppServices.class.getName());
        Call<ServiceResponse> serviceCall = service.initConfiguration(configElement);
        serviceCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response != null) {
                    ServiceResponse serviceResponse = response.body();
                    if (serviceResponse != null) {
                        if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                            ConfigElement output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                            serviceCallback.onSuccess(output);
                        } else {
                            serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                serviceCallback.onError(WebServiceStatus.FAILURE, null);
            }
        });
    }

    public static void validateMobile(final ServiceCallback<String> serviceCallback) {
        RegisterAppServices service = (RegisterAppServices) serviceCallMap.get(RegisterAppServices.class.getName());
        Call<ServiceResponse> serviceCall = service.validateCodeLong("validateCodeLong/1.0" + "/" + SDKUtils.VMN_MESSAGE);
        serviceCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response != null) {
                    ServiceResponse serviceResponse = response.body();
                    if (serviceResponse != null) {
                        if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                            String output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                            serviceCallback.onSuccess(output);
                        } else {
                            serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                serviceCallback.onError(WebServiceStatus.FAILURE, null);
            }
        });
    }

    public static void sendOTPforPasswordReset(UserInfo configElement, final ServiceCallback<String> serviceCallback) {
        RegisterAppServices service = (RegisterAppServices) serviceCallMap.get(RegisterAppServices.class.getName());
        ServiceRequest request = new ServiceRequest();
        request.setRequest(configElement);
        Call<ServiceResponse> serviceCall = service.sendOtpForPwdReset(request);
        serviceCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response != null) {
                    ServiceResponse serviceResponse = response.body();
                    if (serviceResponse != null) {
                        if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                            String output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                            serviceCallback.onSuccess(output);
                        } else {
                            serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                serviceCallback.onError(WebServiceStatus.FAILURE, null);
            }
        });
    }

    public static void validateOTPforPasswordReset(OtpServiceDetails configElement, final ServiceCallback<String> serviceCallback) {
        RegisterAppServices service = (RegisterAppServices) serviceCallMap.get(RegisterAppServices.class.getName());
        ServiceRequest request = new ServiceRequest();
        request.setRequest(configElement);
        Call<ServiceResponse> serviceCall = service.validateOtpForPwdReset(request);
        serviceCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response != null) {
                    ServiceResponse serviceResponse = response.body();
                    if (serviceResponse != null) {
                        if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                            // String output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                            serviceCallback.onSuccess("SUCCESS");
                        } else {
                            serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                serviceCallback.onError(WebServiceStatus.FAILURE, null);
            }
        });
    }

    public static void updatePIN(UpdatePassword configElement, final ServiceCallback<String> serviceCallback) {
        RegisterAppServices service = (RegisterAppServices) serviceCallMap.get(RegisterAppServices.class.getName());
        ServiceRequest request = new ServiceRequest();
        request.setRequest(configElement);
        Call<ServiceResponse> serviceCall = service.updatePassword(request);
        serviceCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response != null) {
                    ServiceResponse serviceResponse = response.body();
                    if (serviceResponse != null) {
                        if (WebServiceStatus.SUCCESS == WebServiceStatus.valueOf(serviceResponse.getResponseCode())) {
                            // String output = JsonUtils.get(serviceResponse.getResult(), serviceCallback.getTypeClass());
                            serviceCallback.onSuccess("SUCCESS");
                        } else {
                            serviceCallback.onError(WebServiceStatus.FAILURE, response.body().getError());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                serviceCallback.onError(WebServiceStatus.FAILURE, null);
            }
        });
    }

  /*  private static boolean checkSSLPin(){
        String hostname = UPPSDKConstants.UPP_SSL_PIN_URL;
        PublicKey keyRemote=null;
        PublicKey keyLocal=null;
        try {
            SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket(hostname, 443);
            socket.startHandshake();
            Certificate[] certs = socket.getSession().getPeerCertificates();
            Certificate cert = certs[0];
            keyRemote = cert.getPublicKey();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca;
            InputStream caInput = UpiSDKContext.applicationContext.getResources().openRawResource(R.raw.cert);
            try {
                ca = cf.generateCertificate(caInput);
                keyLocal=ca.getPublicKey();
            } finally {
                caInput.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        if (keyLocal.equals(keyRemote)){
            return true;
        }else{
            return false;
        }


    }*/

}
