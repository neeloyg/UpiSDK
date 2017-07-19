package com.upi.sdk.services;

import com.rssoftware.upiint.schema.ConfigElement;
import com.rssoftware.upiint.schema.OtpServiceDetails;
import com.rssoftware.upiint.schema.RegisterApp;
import com.rssoftware.upiint.schema.ServiceRequest;
import com.rssoftware.upiint.schema.ServiceResponse;
import com.rssoftware.upiint.schema.User;
import com.upi.sdk.domain.UpdatePassword;
import com.upi.sdk.domain.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by SwapanP on 22-04-2016.
 */
public interface RegisterAppServices {

    /*@POST("userLogin/1.0")
    Call<ServiceResponse> userLogin(@Body User user);*/

    @POST("userLogin/1.0")
    Call<ServiceResponse> userLogin(@Body ServiceRequest request);

    @POST("userRegistration/1.0")
    Call<ServiceResponse> registerUser(@Body ServiceRequest request);

    @POST("registerApp/1.0")
    Call<ServiceResponse> registerApplication(@Body RegisterApp regApp);

    @POST("getToken/1.0")
    Call<ServiceResponse> initiateToken(@Body ServiceRequest request);

    @POST("getListKeys/1.0")
    Call<ServiceResponse> initiateListKeys(@Body ServiceRequest request);

    @POST("sendOtp/1.0")
    Call<ServiceResponse> sendOtp(@Body ServiceRequest request);

    @POST("activateUser/1.0")
    Call<ServiceResponse> validateOtp(@Body ServiceRequest request);

    @POST("fetchConfigParms/1.0")
    Call<ServiceResponse> initConfiguration(@Body ConfigElement request);

    @POST("requestOtp/1.0")
    Call<ServiceResponse> requestOTP(@Body ServiceRequest request);

    @POST
    Call<ServiceResponse> validateCodeLong(@Url String url);

    @POST("sendOtpForPwdReset/1.0")
    Call<ServiceResponse> sendOtpForPwdReset(@Body ServiceRequest request);

    @POST("validateOtp/1.0")
    Call<ServiceResponse> validateOtpForPwdReset(@Body ServiceRequest request);

    @POST("updatePassword/1.0")
    Call<ServiceResponse> updatePassword(@Body ServiceRequest request);

    @POST("deRegisterUser/1.0")
    Call<ServiceResponse> deregisterProfile(@Body ServiceRequest request);

    @POST("updatePassword/1.0")
    Call<ServiceResponse> changeProfilePassword(@Body ServiceRequest request);









}
