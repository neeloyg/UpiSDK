package com.upi.sdk.processflow.bankAccount;

import android.os.Handler;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.ResponseSetCre;
import com.rssoftware.upiint.schema.SetCredential;
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
public class ChangeMpinSubflow implements ProcessSubflow {

    private int counter = 0;
    private int temp_counter = 0;
    private boolean isMPINSet=false;

    public ChangeMpinSubflow() {
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final SetCredential setCredential = (SetCredential) o;
        if(setCredential.getiStateListener()!=null)
            setCredential.getiStateListener().getState(State.ACTIVE);

        if (setCredential.getWaitingTime() != null) {
            long waitTime = Long.parseLong(String.valueOf(setCredential.getWaitingTime()));
            if (waitTime > 0)
                counter = (int) waitTime / 5000;
            counter = counter - 1;
            setCredential.setWaitingTime(null);
            changeMPIN(setCredential, chain);
        } else {
            changeMPIN(setCredential, chain);
        }




    }

    private void changeMPIN(final SetCredential setCredential,final ProcessChain chain){

        if(counter>0){
            temp_counter++;
        }

        UppServices.changeMPIN(setCredential, new ServiceCallback<ResponseSetCre>(ResponseSetCre.class) {
            @Override
            public void onSuccess(final ResponseSetCre result) {
                if (result != null) {
                    if (result.getWaitingTime() != null
                            && result.getWaitingTime().longValue() != 0) {
                        setCredential.setTxnId(result.getTxnId());
                        long waitTime = Long.parseLong(String.valueOf(result.getWaitingTime()));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setCredential.setMode("RESPONSE_SETCRE");
                                setCredential.getPayer().getCreds().clear();
                                setCredential.getPayer().getNewcreds().clear();
                                setCredential.setWaitingTime(result.getWaitingTime());
                                chain.doNext(setCredential);
                            }
                        }, 2000);
                    } else {
                        if(setCredential.getiStateListener()!=null)
                            setCredential.getiStateListener().getState(State.ACTIVE);
                        chain.doNext(result);
                    }
                } else {
                    if(setCredential.getiStateListener()!=null)
                        setCredential.getiStateListener().getState(State.ACTIVE);
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                }


            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if(setCredential.getiStateListener()!=null)
                    setCredential.getiStateListener().getState(State.ACTIVE);


                if(temp_counter==counter){
                    if (errors != null && errors.size() > 0) {
                        if(errors.get(0).getErrorCode()!=null && errors.get(0).getErrorCode().length()>0)
                            chain.breakChain(errors.get(0).getErrorCode(),null);
                        else
                            chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                    } else {
                        chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
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
                                    changeMPIN(setCredential,chain);
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
