package com.upi.sdk.processflow.vpa;

import android.os.Handler;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.RequestValAddress;
import com.rssoftware.upiint.schema.ResponseValAddress;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by AmitKP on 6/8/2016.
 */
public class ValidateVpaSubflow implements ProcessSubflow {
    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final RequestValAddress param=(RequestValAddress)o;

        UppServices.validateVPA(param, new ServiceCallback<ResponseValAddress>(ResponseValAddress.class) {
            @Override
            public void onSuccess(final ResponseValAddress result) {
                if (result != null) {
                    if (result.getWaitingTime() != null
                            && result.getWaitingTime().longValue() != 0) {
                        //long waitTime = Long.parseLong(String.valueOf(result.getWaitingTime()));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                             //   param.setReferanceId(result.getReferanceId());
                                param.setTxnId(result.getTxnId());
                                param.setWaitingTime(result.getWaitingTime());

//                                param.set
                                chain.doNext(o);
                            }
                        }, 3000);
                    } else {
                        chain.doNext(result);
                    }
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00068.name(),null);
                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00068.name(),null);
                }
            }
        });
    }
}
