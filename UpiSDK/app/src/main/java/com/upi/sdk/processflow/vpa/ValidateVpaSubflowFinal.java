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
public class ValidateVpaSubflowFinal implements ProcessSubflow {

    private int counter=0;
    private int temp_counter=0;

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final RequestValAddress param = (RequestValAddress) o;
        long waitTime = Long.parseLong(String.valueOf(param.getWaitingTime()));
        if(waitTime>0)
        counter=(int)waitTime/5000;
        counter=counter-1;
        if(temp_counter<=counter){
            param.setWaitingTime(null);
            validateVPA(param, chain);
        }



    }

    private void validateVPA(final RequestValAddress param, final ProcessChain chain) {
        temp_counter++;
        UppServices.validateVPA(param, new ServiceCallback<ResponseValAddress>(ResponseValAddress.class) {
            @Override
            public void onSuccess(ResponseValAddress result) {
                if (result != null && result.getResult()!=null && result.getResult().length()>0) {
                    chain.doNext(result);
                } else {
                    if (temp_counter == counter) {
                        chain.breakChain(SDKErrorCodes.ERR00068.name(),null);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                validateVPA(param, chain);
                            }
                        }, 3000);

                    }

                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if(temp_counter==counter){
                    if (errors != null && errors.size() > 0) {
                        if(errors.get(0).getErrorCode()!=null && errors.get(0).getErrorCode().length()>0)
                            chain.breakChain(errors.get(0).getErrorCode(),null);
                        else
                            chain.breakChain(SDKErrorCodes.ERR00030.name(),null);
                    } else {
                        chain.breakChain(SDKErrorCodes.ERR00030.name(),null);
                    }
                }else{
                    try {
                        if (errors != null && errors.size() > 0) {
                            if (errors.get(0).getUpiErrorCode() != null && errors.get(0).getUpiErrorCode().length() > 0) {
                                chain.breakChain(SDKErrorCodes.ERR00096.name(),"ERR000"+errors.get(0).getUpiErrorCode());
                            }
                        }else{
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    validateVPA(param, chain);
                                }
                            }, 3000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }
}
