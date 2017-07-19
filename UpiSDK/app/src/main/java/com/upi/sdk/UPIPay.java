package com.upi.sdk;

import android.content.Context;

import com.rssoftware.upiint.schema.LinkAccount;
import com.rssoftware.upiint.schema.TransactionHistory;
import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.APICallback;
import com.upi.sdk.domain.InListLinkAccountQuery;
import com.upi.sdk.domain.InListVPAQuery;
import com.upi.sdk.domain.InPayRequest;
import com.upi.sdk.domain.InPaymentAuthorization;
import com.upi.sdk.domain.InPendingTxnQuery;
import com.upi.sdk.domain.InTxnHistoryQuery;
import com.upi.sdk.domain.InputAddBankAccount;
import com.upi.sdk.domain.InputAddVPA;
import com.upi.sdk.domain.InputDeleteBankAccount;
import com.upi.sdk.domain.InputDeleteVPA;
import com.upi.sdk.domain.InputUpdateVPA;
import com.upi.sdk.domain.InputcheckVPAAvailability;
import com.upi.sdk.domain.OutPayRequest;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.errors.UPISDKException;
import com.upi.sdk.processflow.ProcessFlow;
import com.upi.sdk.processflow.bankAccount.AddBankAccConverterSubflow;
import com.upi.sdk.processflow.bankAccount.BankAccAdditionSubflow;
import com.upi.sdk.processflow.bankAccount.BankAccDeletionSubflow;
import com.upi.sdk.processflow.bankAccount.BankAccListingSubflow;
import com.upi.sdk.processflow.bankAccount.DeleteBankAccConverterSubflow;
import com.upi.sdk.processflow.bankAccount.ListBankAccConverterSubflow;
import com.upi.sdk.processflow.transaction.CredentialCaptureAuthSubflow;
import com.upi.sdk.processflow.transaction.CredentialCaptureSubflow;
import com.upi.sdk.processflow.transaction.InPayReqConverterSubflow;
import com.upi.sdk.processflow.transaction.PaymentAuthRequestConverterSubflow;
import com.upi.sdk.processflow.transaction.PaymentAuthorizationSubflow;
import com.upi.sdk.processflow.transaction.PaymentInitiatorSubflow;
import com.upi.sdk.processflow.transaction.PendingTxnListPopulatorSubflow;
import com.upi.sdk.processflow.transaction.TransactionHistoryPopulatorSubflow;
import com.upi.sdk.processflow.transaction.TransactionIdGeneratorSubflow;
import com.upi.sdk.processflow.vpa.AddVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.CheckVpaAvailabilityConverterSubflow;
import com.upi.sdk.processflow.vpa.CheckVpaAvailabilitySubflow;
import com.upi.sdk.processflow.vpa.DeleteVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.ListVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.UpdateVpaConverterSubflow;
import com.upi.sdk.processflow.vpa.VpaAdditionSubflow;
import com.upi.sdk.processflow.vpa.VpaDeletionSubflow;
import com.upi.sdk.processflow.vpa.VpaPopulatorSubflow;
import com.upi.sdk.processflow.vpa.VpaUpdationSubflow;

import java.util.List;

/**
 * Created by neeloyg on 20-04-2016.
 */
public final class UPIPay {

    private final UpiSDKContext context;

    private UPIPay(UpiSDKContext context) {
        this.context = context;
    }
    /**
     * Initializing the SDK
     */
    public static void initializeSDK(Context applicationContext, String customerRef, String mobileNumber,
                                     String passphrase, APICallback<com.upi.sdk.core.UpiSDKContext.UPIPay> initCallback)
            throws UPISDKException {
        /*UpiSDKContext.initialize(applicationContext, customerRef, mobileNumber,
                passphrase, initCallback);*/
    }

    /**
     * Managing Bank Accounts
     * */

    public static void addBankAccount(InputAddBankAccount input,final APICallback<String> callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(input,callback)
                .addNext(new AddBankAccConverterSubflow())
                .addNext(new BankAccAdditionSubflow())
                .execute();
    }

    public static void listBankAccount(InListLinkAccountQuery qry, final APICallback<List<LinkAccount>> callback)
            throws UPISDKException {

        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(qry,callback)
                .addNext(new ListBankAccConverterSubflow())
                .addNext(new BankAccListingSubflow())
                .execute();
    }


    public static void deleteBankAccount(InputDeleteBankAccount input,final APICallback<String> callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(input,callback)
                .addNext(new DeleteBankAccConverterSubflow())
                .addNext(new BankAccDeletionSubflow())
                .execute();
    }


    /**
     * Managing Virtual Payment Address
     */

    public static void checkVPAAvailability(InputcheckVPAAvailability input,final APICallback<Boolean> callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(input,callback)
                .addNext(new CheckVpaAvailabilityConverterSubflow())
                .addNext(new CheckVpaAvailabilitySubflow())
                .execute();
    }

    public static void addVPA(InputAddVPA input,final APICallback<String> callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(input,callback)
                .addNext(new AddVpaConverterSubflow())
                .addNext(new VpaAdditionSubflow())
                .execute();
    }

    public static void updateVPA(InputUpdateVPA input,final APICallback<String> callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(input,callback)
                .addNext(new UpdateVpaConverterSubflow())
                .addNext(new VpaUpdationSubflow())
                .execute();

    }

    public static void deleteVPA(InputDeleteVPA input,final APICallback<String> callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(input,callback)
                .addNext(new DeleteVpaConverterSubflow())
                .addNext(new VpaDeletionSubflow())
                .execute();
    }



    public static void listVPA(InListVPAQuery qry, final APICallback<List<Vpa>> callback) throws UPISDKException {

        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(qry,callback)
                .addNext(new ListVpaConverterSubflow())
                .addNext(new VpaPopulatorSubflow())
                .execute();
    }


    /**
     * Making a payment request
     */
    public static void initiatePayment(InPayRequest payRequest, final APICallback<OutPayRequest> callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103); 
        }

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
    public static void initiateCollect(InPayRequest payRequest, final APICallback<OutPayRequest> callback) throws UPISDKException {
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
    public static void getTransactionHistory(InTxnHistoryQuery txnHistory, final APICallback<List<TransactionHistory>> callback)  throws UPISDKException {

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
    public static void authorizeCollectionRequest(InPaymentAuthorization authPaymentRequest, APICallback callback) throws UPISDKException {
        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(authPaymentRequest, callback)
                .addNext(new PaymentAuthRequestConverterSubflow())
                .addNext(new CredentialCaptureAuthSubflow())
                .addNext(new PaymentAuthorizationSubflow())
                .execute();
    }

    /**
     * Pending Collect Request
     */
    public static void getPendingCollectRequests(InPendingTxnQuery pendingTxnQuery, final APICallback<List<TransactionHistory>> callback)  throws UPISDKException {

        if (!UpiSDKContext.isInitialized()) {
            throw new UPISDKException(SDKErrorCodes.ERR00103);
        }

        ProcessFlow.create(pendingTxnQuery, callback)
                .addNext(new PendingTxnListPopulatorSubflow())
                .execute();
    }
}
