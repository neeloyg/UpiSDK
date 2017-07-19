package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.PaymentRequest;
import com.upi.sdk.domain.OutPayRequest;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class PaymentInitiatorSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        PaymentRequest payReq = (PaymentRequest)o;
        payReq.setBank_name(null);
        final String txnId = payReq.getTxnId();
        payReq.setBank_name(null);
        UppServices.initiateCollect(payReq, new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {

                OutPayRequest out = new OutPayRequest();
                out.setTxnId(txnId);
                if ("SUCCESS".equals(result)) {
                    out.setStatus(result);
                    out.setTxnId(txnId);
                } else {
                    out.setTxnId(txnId);
                    out.setStatus(result);
                }
                out.setStatus("Transaction initiated successfully");
                chain.doNext(out);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && errors.size() > 0) {
                    if(errors.get(0).getErrorCode()!=null && errors.get(0).getErrorCode().length()>0)
                        chain.breakChain(errors.get(0).getErrorCode(),null);
                    else
                        chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
                }
            }
        });
    }
}
