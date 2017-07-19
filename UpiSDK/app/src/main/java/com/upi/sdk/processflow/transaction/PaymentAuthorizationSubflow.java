package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.PaymentAuthRequest;
import com.rssoftware.upiint.schema.State;
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
public class PaymentAuthorizationSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final PaymentAuthRequest payAuthReq = (PaymentAuthRequest) o;
        final String txnId = payAuthReq.getTxnId();
        UppServices.authorizePaymentRequest(payAuthReq, new ServiceCallback<Void>(Void.class) {
            @Override
            public void onSuccess(Void result) {

                /*OutPayRequest out = new OutPayRequest();
                if ("SUCCESS".equals(result)) {
                    out.setStatus(result);
                    out.setTxnId(txnId);
                } else {
                    out.setStatus(result);
                }*/
                if (payAuthReq.getiStateListener() != null)
                    payAuthReq.getiStateListener().getState(State.ACTIVE);
                chain.doNext(true);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (payAuthReq.getiStateListener() != null)
                    payAuthReq.getiStateListener().getState(State.ACTIVE);
                if (errors != null && errors.size() > 0) {
                    if (errors.get(0).getErrorCode() != null && errors.get(0).getErrorCode().length() > 0)
                        chain.breakChain(errors.get(0).getErrorCode(),null);
                    else
                        chain.breakChain(SDKErrorCodes.ERR00101.name(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00101.name(),null);
                }
            }
        });
    }
}
