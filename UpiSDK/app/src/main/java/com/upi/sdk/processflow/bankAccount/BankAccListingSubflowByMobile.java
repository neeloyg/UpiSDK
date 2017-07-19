package com.upi.sdk.processflow.bankAccount;

import android.os.Handler;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.ListParm;
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
public class BankAccListingSubflowByMobile implements ProcessSubflow {
    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final ListParm param=(ListParm)o;

        UppServices.listBankAccountsByMobile(param, new ServiceCallback<ListParm>(ListParm.class) {
            @Override
            public void onSuccess(final ListParm result) {
                if(result!=null ) {
                    if(result.getWaitingTime() != null
                            && result.getWaitingTime().longValue() != 0){
                        //long waitTime = Long.parseLong(String.valueOf(result.getWaitingTime()));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                param.setReferanceId(result.getReferanceId());
                                param.setWaitingTime(result.getWaitingTime());
                                chain.doNext(o);
                            }
                        }, 2000);
                    }else {
                        chain.doNext(result);
                    }
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00030.name(),null);
                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00048.name(),null);
                }
            }
        });
    }
}
