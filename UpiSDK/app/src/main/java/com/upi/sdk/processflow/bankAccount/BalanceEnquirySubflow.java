package com.upi.sdk.processflow.bankAccount;

import android.os.Handler;

import com.rssoftware.upiint.schema.BalEnquiryResp;
import com.rssoftware.upiint.schema.BalanceEnquiry;
import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.State;
import com.upi.sdk.domain.BalanceEnquiryResponse;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class BalanceEnquirySubflow implements ProcessSubflow {

    private boolean responseMode;
    private int counter = 0;
    private int temp_counter = 0;
    private boolean isBalanceFetched=false;

    public BalanceEnquirySubflow(boolean responseMode) {
        this.responseMode = responseMode;
    }

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final BalanceEnquiry balanceEnquiry = (BalanceEnquiry) o;
        balanceEnquiry.setResponseCall(responseMode);
        if(balanceEnquiry.getiStateListener()!=null)
        balanceEnquiry.getiStateListener().getState(State.ACTIVE);
        if (balanceEnquiry.getWaitingTime() != null) {
            long waitTime = Long.parseLong(String.valueOf(balanceEnquiry.getWaitingTime()));
            if (waitTime > 0)
                counter = (int) waitTime / 5000;
            counter = counter - 1;
            balanceEnquiry.setWaitingTime(null);
            getBalance(balanceEnquiry, chain);
        } else {
            getBalance(balanceEnquiry, chain);
        }


    }

    private void getBalance(final BalanceEnquiry balanceEnquiry, final ProcessChain chain) {
        if(counter>0){
            temp_counter++;
        }

        UppServices.getAccountBalance(balanceEnquiry, new ServiceCallback<BalEnquiryResp>(BalEnquiryResp.class) {
            @Override
            public void onSuccess(final BalEnquiryResp result) {

                if (result != null && responseMode && result.getBalance()!=null && result.getBalance().length()>0) {
                    BalanceEnquiryResponse response = new BalanceEnquiryResponse();
                    BigDecimal balance = null;
                    try {
                        balance = BigDecimal.valueOf(Double.valueOf(result.getBalance()));
                    } catch (Exception e) {
                        // Ignore
                    }

                    response.setAccountBalance(balance);
                    isBalanceFetched=true;
                    if(balanceEnquiry.getiStateListener()!=null)
                        balanceEnquiry.getiStateListener().getState(State.ACTIVE);
                    chain.doNext(response);
                }

                if (result != null && !responseMode && result.getWaitingTime() != null
                        && result.getWaitingTime().longValue() != 0 && !isBalanceFetched) {
                   // long waitTime = Long.parseLong(String.valueOf(result.getWaitingTime()));
                    if(counter==0){
                        balanceEnquiry.getCreds().clear();
                        balanceEnquiry.setWaitingTime(result.getWaitingTime());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chain.doNext(balanceEnquiry);
                            }
                        }, 2000);

                    }else{
                        if(temp_counter==counter){
                            if(balanceEnquiry.getiStateListener()!=null)
                                balanceEnquiry.getiStateListener().getState(State.ACTIVE);
                            chain.breakChain(SDKErrorCodes.ERR00095.name(),null);
                        }else{
                           new Handler().postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   getBalance(balanceEnquiry, chain);
                               }
                           },3000);

                        }
                    }

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            balanceEnquiry.getCreds().clear();
//                            balanceEnquiry.setWaitingTime(result.getWaitingTime());
//
//                        }
//                    }, waitTime);
                }else if(!isBalanceFetched){
                    if(temp_counter==counter){
                        if(balanceEnquiry.getiStateListener()!=null)
                            balanceEnquiry.getiStateListener().getState(State.ACTIVE);
                        chain.breakChain(SDKErrorCodes.ERR00095.name(),null);
                    }else{
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getBalance(balanceEnquiry, chain);
                            }
                        },3000);

                    }
                }

            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if(balanceEnquiry.getiStateListener()!=null)
                    balanceEnquiry.getiStateListener().getState(State.ACTIVE);
                if(temp_counter==counter){
                    if (errors != null && errors.size() > 0) {
                        if(errors.get(0).getErrorCode()!=null && errors.get(0).getErrorCode().length()>0)
                        chain.breakChain(errors.get(0).getErrorCode(),null);
                        else
                        chain.breakChain(SDKErrorCodes.ERR00095.name(),null);
                    } else {
                        chain.breakChain(SDKErrorCodes.ERR00095.name(),null);
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
                                    getBalance(balanceEnquiry, chain);
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
