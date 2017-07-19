package com.upi.sdk.services;

import com.rssoftware.upiint.schema.ServiceRequest;
import com.rssoftware.upiint.schema.ServiceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by SwapanP on 22-04-2016.
 */
public interface TransactionService {

    @POST("getTransactionID/1.0")
    Call<ServiceResponse> getTransactionId(@Body ServiceRequest request);

    @POST("createPaymentRequest/1.0")
    Call<ServiceResponse> initiatePayment(@Body ServiceRequest request);

    @POST("listTxnHistory/1.0")
    Call<ServiceResponse> getAllTransactions(@Body ServiceRequest request);

    @POST("listTxnHistbyDate/1.0")
    Call<ServiceResponse> getAllTransactionsByDateFilter(@Body ServiceRequest request);

    @POST("createPaymentRequest/1.0")
    Call<ServiceResponse> initiateCollect(@Body ServiceRequest request);

    @POST("authPayment/1.0")
    Call<ServiceResponse> authorizeTransaction(@Body ServiceRequest request);

    @POST("listAuthPendingTxns/1.0")
    Call<ServiceResponse> getAllPendingTransaction(@Body ServiceRequest request);

    @POST("raiseDispute/1.0")
    Call<ServiceResponse> raiseDispute(@Body ServiceRequest request);

    @POST("fetchTransaction/1.0")
    Call<ServiceResponse> transactionHistoryDetails(@Body ServiceRequest request);

    @POST("listTxnHistbyRefId/1.0")
    Call<ServiceResponse> transactionHistoryDetailsByRefId(@Body ServiceRequest request);

    @POST("searchNotificationMessage/1.0")
    Call<ServiceResponse> getAllNotifications(@Body ServiceRequest request);

    @POST("fetchChannelId/1.0")
    Call<ServiceResponse> fetchChannelId(@Body ServiceRequest request);

    @POST("listTxnHistbyCustRefId/1.0")
    Call<ServiceResponse> getAllTransactionsByCustRefId(@Body ServiceRequest request);

    @POST("isVerifiedVae/1.0")
    Call<ServiceResponse> isVerifiedVae(@Body ServiceRequest request);

    @POST("listTxnHistorybyVpa/1.0")
    Call<ServiceResponse> getAllTransactionsByVPA(@Body ServiceRequest request);

    @POST("calculateServiceCharge/1.0")
    Call<ServiceResponse> calculateServiceCharge(@Body ServiceRequest request);
}
