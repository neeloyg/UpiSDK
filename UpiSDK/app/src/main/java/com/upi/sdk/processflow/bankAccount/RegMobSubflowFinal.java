package com.upi.sdk.processflow.bankAccount;

import android.os.Handler;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.ReqRegMobile;
import com.rssoftware.upiint.schema.RespRegMobile;
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
public class RegMobSubflowFinal implements ProcessSubflow {
    private int counter=0;
    private int temp_counter=0;

    public RegMobSubflowFinal() {
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final ReqRegMobile reqRegMobile = (ReqRegMobile) o;
        long waitTime = Long.parseLong(String.valueOf(reqRegMobile.getWaitingTime()));
        if(waitTime>0)
            counter=(int)waitTime/5000;
        counter=counter-1;
        reqRegMobile.setWaitingTime(null);
        if(temp_counter<=counter){
            registerMobileBanking(reqRegMobile, chain);
        }




    }

    private void registerMobileBanking(final ReqRegMobile reqRegMobile, final ProcessChain chain){
        temp_counter++;
        UppServices.registerMobileBanking(reqRegMobile, new ServiceCallback<RespRegMobile>(RespRegMobile.class) {
            @Override
            public void onSuccess(final RespRegMobile result) {
                if(result!=null ) {
                    if(result.getWaitingTime() != null
                            && result.getWaitingTime().longValue() != 0){
                        if(temp_counter==counter){
                            chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                        }else{
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    registerMobileBanking(reqRegMobile,chain);
                                }
                            }, 3000);
                        }

                    }else {
                        if(reqRegMobile.getiStateListener()!=null)
                            reqRegMobile.getiStateListener().getState(State.ACTIVE);
                        chain.doNext(result);
                    }
                }else{
                    if(reqRegMobile.getiStateListener()!=null)
                        reqRegMobile.getiStateListener().getState(State.ACTIVE);
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                }


            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if(temp_counter==counter) {
                    if(reqRegMobile.getiStateListener()!=null)
                        reqRegMobile.getiStateListener().getState(State.ACTIVE);
                    if (errors != null && errors.size() > 0) {
                        if(errors.get(0).getErrorCode()!=null && errors.get(0).getErrorCode().length()>0)
                            chain.breakChain(errors.get(0).getErrorCode(),null);
                        else
                            chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                    } else {
                        chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                    }
                }else {
                    if (errors != null && errors.size() > 0) {
                        if (errors.get(0).getUpiErrorCode() != null && errors.get(0).getUpiErrorCode().length() > 0) {
                            chain.breakChain(SDKErrorCodes.ERR00096.name(),"ERR000"+errors.get(0).getUpiErrorCode());
                        }
                    }else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                registerMobileBanking(reqRegMobile, chain);
                            }
                        }, 3000);
                    }
                }
            }
        });
    }
}
