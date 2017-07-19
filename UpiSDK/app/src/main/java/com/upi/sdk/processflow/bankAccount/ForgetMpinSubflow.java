package com.upi.sdk.processflow.bankAccount;

import android.os.Handler;
import android.util.Log;

import com.rssoftware.upiint.schema.Cred;
import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.NewCred;
import com.rssoftware.upiint.schema.Payer;
import com.rssoftware.upiint.schema.ReqRegMobile;
import com.rssoftware.upiint.schema.ResponseSetCre;
import com.rssoftware.upiint.schema.SetCredential;
import com.rssoftware.upiint.schema.State;
import com.upi.sdk.core.UpiSDKContext;
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
public class ForgetMpinSubflow implements ProcessSubflow {

    public ForgetMpinSubflow() {
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {
        final ReqRegMobile request = (ReqRegMobile) o;
        final SetCredential setCredential = new SetCredential();
        setCredential.setTxnId(request.getTxnId());

        Payer payer = new Payer();
        payer.setIfsc(request.getIfsc());
        payer.setPayerUserId(UpiSDKContext.getInstance().getUserId());
        payer.setPayerAcVpa(UpiSDKContext.getInstance().getUserDefaultVpa());
        setCredential.setPayer(payer);
//        if (request.getCredAllowed() != null)
//            setCredential.setCredAllowed(request.getCredAllowed());

        if(request.getCreds()!=null && request.getCreds().size()>0) {
            setCredential.setMode("REQUEST_SETCRE");
            for (Cred cred : request.getCreds()) {
                if ("MPIN".equalsIgnoreCase(cred.getSubtype())) {
                    Log.d("ForgetMpin", "New Cred added");
                    NewCred newCred = new NewCred();
                    newCred.setData(cred.getData());
                    newCred.setType(cred.getType());
                    newCred.setSubtype("MPIN");
                    setCredential.getPayer().getNewcreds().add(newCred);
                } else if ("SMS".equalsIgnoreCase(cred.getSubtype())) {
                    Log.d("ForgetMpin", "Current Cred added");
                   // cred.setType("PIN");
                   // cred.setSubtype("MPIN");
                    setCredential.getPayer().getCreds().add(cred);
                }


            }
        }else{
            setCredential.setMode("RESPONSE_SETCRE");
        }


        UppServices.changeMPIN(setCredential, new ServiceCallback<ResponseSetCre>(ResponseSetCre.class) {
            @Override
            public void onSuccess(final ResponseSetCre result) {
                if (result != null) {
                    if (result.getWaitingTime() != null
                            && result.getWaitingTime().longValue() != 0) {
                        setCredential.setTxnId(result.getTxnId());
                        request.setTxnId(result.getTxnId());
                        request.getCreds().clear();
                        long waitTime = Long.parseLong(String.valueOf(result.getWaitingTime()));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setCredential.setMode("RESPONSE_SETCRE");
                                setCredential.getPayer().getCreds().clear();
                                setCredential.getPayer().getNewcreds().clear();
                                chain.doNext(o);
                            }
                        }, waitTime);
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
                if (errors != null && errors.size() > 0) {
                    if(errors.get(0).getErrorCode()!=null && errors.get(0).getErrorCode().length()>0)
                        chain.breakChain(errors.get(0).getErrorCode(),null);
                    else
                        chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                }
            }
        });


    }
}
