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
public class RegMobSubflow implements ProcessSubflow {

    public RegMobSubflow() {
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final ReqRegMobile reqRegMobile = (ReqRegMobile) o;
        if (reqRegMobile.getiStateListener() != null)
            reqRegMobile.getiStateListener().getState(State.ACTIVE);

        UppServices.registerMobileBanking(reqRegMobile, new ServiceCallback<RespRegMobile>(RespRegMobile.class) {
            @Override
            public void onSuccess(final RespRegMobile result) {
                if(result!=null ) {
                    if(result.getWaitingTime() != null
                            && result.getWaitingTime().longValue() != 0){
                    reqRegMobile.setTxnId(result.getTxnId());
                    long waitTime = Long.parseLong(String.valueOf(result.getWaitingTime()));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reqRegMobile.getCreds().clear();
                            reqRegMobile.setWaitingTime(result.getWaitingTime());
                            chain.doNext(o);
                        }
                    }, 2000);
                }else {
                    chain.doNext(result);
                }
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                }


            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                }
            }
        });


    }
}
