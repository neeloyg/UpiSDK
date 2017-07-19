package com.upi.sdk.services;

import com.rssoftware.upiint.schema.PaymentRequest;
import com.rssoftware.upiint.schema.ServiceRequest;
import com.rssoftware.upiint.schema.ServiceResponse;
import com.rssoftware.upiint.schema.UserValidation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by SwapanP on 22-04-2016.
 */
public interface ManagementService {

    // Bank Account Management

    @POST("addLinkAccount/1.0")
    Call<ServiceResponse> addBankAccount(@Body ServiceRequest request);

    @POST("addActiveAccount/1.0")
    Call<ServiceResponse> addBankAccountByMobile(@Body ServiceRequest request);

    @POST("fetchAccForMobile/1.0")
    Call<ServiceResponse> fetchBankAccountForMobile(@Body ServiceRequest request);

    @POST("fetchBank/1.0")
    Call<ServiceResponse> fetchBanks(@Body ServiceRequest request);

    @POST("listLinkedAccounts/1.0")
    Call<ServiceResponse> listBankAccounts(@Body ServiceRequest request);

    @POST("fetchAccountList/1.0")
    Call<ServiceResponse> listBankAccountsByMobile(@Body ServiceRequest request);

    @POST("delLinkAccount/1.0")
    Call<ServiceResponse> deleteBankAccount(@Body ServiceRequest request);

    // VPA Management

    @POST("isVpaAvailable/1.0")
    Call<ServiceResponse> checkVPAAvailability(@Body ServiceRequest userValidation);

    @POST("addVPA/1.0")
    Call<ServiceResponse> addVPA(@Body ServiceRequest request);

    @POST("updateVPA/1.0")
    Call<ServiceResponse> updateVPA(@Body ServiceRequest request);

    @POST("deleteVPA/1.0")
    Call<ServiceResponse> deleteVPA(@Body ServiceRequest request);

    @POST("fetchVPA/1.0")
    Call<ServiceResponse> listVPA(@Body ServiceRequest request);

    @POST("addBeneficiary/1.0")
    Call<ServiceResponse> addBeneficiary(@Body ServiceRequest request);

    @POST("updateBeneficiary/1.0")
    Call<ServiceResponse> updateBeneficiary(@Body ServiceRequest request);

    @POST("removeBeneficiary/1.0")
    Call<ServiceResponse> deleteBeneficiary(@Body ServiceRequest request);

    @POST("listBeneficiaries/1.0")
    Call<ServiceResponse> listBeneficiary(@Body ServiceRequest request);

    @POST("balEnquiry/1.0")
    Call<ServiceResponse> getAccountBalance(@Body ServiceRequest request);

    @POST("requestRegMobile/1.0")
    Call<ServiceResponse> registerMobileBanking(@Body ServiceRequest request);

    @POST("setCredential/1.0")
    Call<ServiceResponse> changeMPIN(@Body ServiceRequest request);

    @POST("reqValAdd/1.0")
    Call<ServiceResponse> validateVPA(@Body ServiceRequest request);

    @POST("activateVPA/1.0")
    Call<ServiceResponse> activateVPA(@Body ServiceRequest request);

    @POST("deActivateVPA/1.0")
    Call<ServiceResponse> deActivateVPA(@Body ServiceRequest request);

    //Schedule Management

    @POST("addSchedule/1.0")
    Call<ServiceResponse> addSchedule(@Body ServiceRequest request);

    @POST("removeSchedule/1.0")
    Call<ServiceResponse> removeSchedule(@Body ServiceRequest request);

    @POST("listSchedule/1.0")
    Call<ServiceResponse> listSchedule(@Body ServiceRequest request);

    @POST("updateSchedule/1.0")
    Call<ServiceResponse> updateSchedule(@Body ServiceRequest request);

    @POST("addOrUpdateUserlimit/1.0")
    Call<ServiceResponse> addOrUpdateUserlimit(@Body ServiceRequest request);


}
